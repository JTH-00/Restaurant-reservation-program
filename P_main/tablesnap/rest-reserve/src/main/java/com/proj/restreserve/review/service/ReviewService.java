package com.proj.restreserve.review.service;

import com.proj.restreserve.alarm.dto.AlarmDto;
import com.proj.restreserve.alarm.service.AlarmService;
import com.proj.restreserve.detailpage.service.FileCURD;
import com.proj.restreserve.payment.dto.PaymentMenuDto;
import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.repository.PaymentRepository;
import com.proj.restreserve.payment.service.PaymentService;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.review.dto.ReviewAndReplyDto;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.dto.ReviewReplyDto;
import com.proj.restreserve.review.dto.SelectReviewDto;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.review.entity.ReviewImage;
import com.proj.restreserve.review.entity.ReviewReply;
import com.proj.restreserve.review.repository.ReviewImageRepository;
import com.proj.restreserve.review.repository.ReviewReplyRepository;
import com.proj.restreserve.review.repository.ReviewRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.repository.VisitRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final VisitRepository visitRepository;
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final FileCURD fileCURD;
    private final RestaurantRepository restaurantRepository;
    private final ReviewReplyRepository reviewReplyRepository;
    private final AlarmService alarmService;
    private final EntityManager entityManager;
    private final String useServiceName = "review";//S3 버킷 폴더명
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }
    private static String getQueryString(boolean replycheck, Boolean sort,Pageable pageable) {
        String sortDESC = sort ? "ORDER BY date DESC, scope DESC" :"ORDER BY date DESC";
        String checkReply = replycheck ? "AND rr.reviewreplyid IS NULL" : "";
        String joinLeft = replycheck ? "LEFT JOIN Reviewreply rr ON r.reviewid = rr.reviewid":"";

        String queryString = "SELECT r.reviewid, r.content, r.date, r.paymentid, r.scope, r.userid, r.visitid FROM Review r " +
                joinLeft + "INNER JOIN Payment p ON p.paymentid = r.paymentid " +
                "WHERE p.restaurantid = :restaurantid " +
                "UNION " +
                "SELECT r.reviewid, r.content, r.date, r.paymentid, r.scope, r.userid, r.visitid FROM Review r " +
                joinLeft + "INNER JOIN Visit v ON v.visitid = r.visitid " +
                "WHERE v.restaurantid = :restaurantid " + checkReply + sortDESC +
                " LIMIT " + pageable.getPageSize() +
                " OFFSET " + pageable.getOffset();;

        return queryString;
    }

    //레스토랑 id로 리뷰찾기
    Page<Review> findReviewsByRestaurant(String restaurantid, Pageable pageable, boolean sortScope, boolean replycheck){
        String queryString = getQueryString(replycheck,sortScope,pageable);
/*        String queryCount = getQueryCount(replycheck);

        int count = ((Number) entityManager.createNativeQuery(queryCount)
                .setParameter("restaurantid", restaurantid)
                .getSingleResult()).intValue();*/

        List<Review> reviews = entityManager.createNativeQuery(queryString, Review.class)
                .setParameter("restaurantid", restaurantid)
                .getResultList();
        return new PageImpl<>(reviews, pageable, reviews.size());
    }
    @Transactional
    public SelectReviewDto writeReview(String visitid, String paymentid,ReviewDto reviewDto, List<MultipartFile> files) {
        // 리뷰 정보 저장
        Review review = new Review();
        // 사용자 인증 정보 가져오기
        User user = getCurrentUser();
        Restaurant restaurant=null;
        // 방문 정보 또는 결제 정보 가져오기
        if(visitid!=null){
            Visit visit= visitRepository.getReferenceById(visitid);
            review.setVisit(visit);
            restaurant = visit.getRestaurant();
        } else if (paymentid!=null) {
            Payment payment = paymentRepository.getReferenceById(paymentid);
            review.setPayment(payment);
            restaurant = payment.getRestaurant();
        } else{
            throw new RuntimeException("포장예약과 방문예약이 둘다 없을 수 없습니다.");
        }
        // 리뷰 작성
        String reviewid= UUID.randomUUID().toString();

        review.setReviewid(reviewid);
        review.setScope(reviewDto.getScope());
        review.setContent(reviewDto.getContent());
        review.setDate(LocalDate.now());
        review.setUser(user);
        //매장 리뷰 카운트 추가, Null에러 반환
        Objects.requireNonNull(restaurant,"해당 매장을 찾을 수 없습니다.").setReviewcount(restaurant.getReviewcount()+1);
        //리뷰 이미지에 set하기위해 save 선언
        reviewRepository.save(review);

        List<ReviewImage> reviewImages = new ArrayList<>();
        // 각 파일에 대한 처리
        if (files != null) {
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString();

                    String imageUrl = fileCURD.uploadImageToS3(file,useServiceName,fileName);//파일 업로드 파일,폴더명,파일일련번호

                    // 리뷰 이미지 정보 생성
                    ReviewImage reviewImage = new ReviewImage();
                    reviewImage.setReviewimageid(fileName);
                    reviewImage.setReview(review);
                    reviewImage.setImagelink(imageUrl);

                    // 이미지 정보 저장
                    reviewImages.add(reviewImage);
                    reviewImageRepository.save(reviewImage);
                }
            }
        }
        //출력을 위해 삽입
        review.setReviewimages(reviewImages);
        SelectReviewDto selectReviewDto = modelMapper.map(review,SelectReviewDto.class);
        List<String> imagelink = review.getReviewimages().stream()
                .map(ReviewImage::getImagelink)
                .collect(Collectors.toList());
        selectReviewDto.setIamgeLinks(imagelink);

        //알람을 해당 매장 업주에게 전송
        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setContent("새로운 리뷰가 작성되었어요.");
        alarmDto.setUrl("api/admin/mypage/review/"+reviewid);// 작성된 리뷰를 조회하도록 url제공
        alarmService.wirteAlarm(alarmDto,"리뷰",restaurant.getUser());//레스토랑 업주에게 보내는 알람

        return selectReviewDto;
    }
    @Transactional
    public SelectReviewDto modifyReview(String reviewid, ReviewDto reviewDto, List<MultipartFile> files, List<String> deleteImageLinks){
        Review review = this.reviewRepository.getReferenceById(reviewid);
        if(!review.getUser().equals(getCurrentUser())){
            throw new RuntimeException("올바른 접근이 아닙니다");
        }
        // 리뷰 작성
        review.setScope(reviewDto.getScope());
        review.setContent(reviewDto.getContent());

        //리뷰 이미지에 set하기위해 save 선언
        reviewRepository.save(review);

        if (deleteImageLinks != null){
            for (String deleteImageLink : deleteImageLinks) {
                //링크의 맨마지막 일련번호를 가져옴
                int imageidNum = deleteImageLink.lastIndexOf("/");
                String imageid = deleteImageLink.substring(imageidNum + 1);

                fileCURD.deleteFile(useServiceName,imageid);//버킷 폴더명,이미지 일련번호
                //orphanRemoval = true가 되어있어 리뷰 엔티티의 연결을 끊어 고아 객체를 삭제
                review.getReviewimages().removeIf(c -> Objects.equals(c.getReviewimageid(), imageid));
            }
        }
        if(files!=null) {
            // 각 파일에 대한 처리
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString();

                    String imageUrl = fileCURD.uploadImageToS3(file, useServiceName, fileName);//파일 업로드 파일,폴더명,파일일련번호
                    // 리뷰 이미지 정보 생성
                    ReviewImage reviewImage = new ReviewImage();
                    reviewImage.setReviewimageid(fileName);
                    reviewImage.setImagelink(imageUrl);
                    reviewImage.setReview(review);
                    review.getReviewimages().add(reviewImage);//리뷰에 있는 컬렉션에 리뷰이미지 추가
                    
                    reviewImageRepository.save(reviewImage);
                }
            }
        }

        SelectReviewDto selectReviewDto = modelMapper.map(review,SelectReviewDto.class);
        List<String> imagelink = review.getReviewimages().stream()
                .map(ReviewImage::getImagelink)
                .collect(Collectors.toList());
        selectReviewDto.setIamgeLinks(imagelink);

        return selectReviewDto;
    }
    @Transactional
    public void deleteReview(String reviewid){
        Restaurant restaurant =null;
        Review review = reviewRepository.getReferenceById(reviewid);
        //리뷰 카운트를 내리기 위해 추출
        if(review.getPayment()!=null){
            restaurant = review.getPayment().getRestaurant();
        }else if(review.getVisit()!=null){
            restaurant = review.getVisit().getRestaurant();
        }
        
        if(!review.getUser().equals(getCurrentUser())){
            throw new RuntimeException("올바른 접근이 아닙니다");
        }
        List<ReviewImage> reviewImages = reviewImageRepository.findByReview_Reviewid(reviewid); //리뷰와 연관된 이미지들

        for(ReviewImage reviewImage: reviewImages){//S3 업로드된 이미지들 삭제
            reviewImageRepository.delete(reviewImage);//리뷰이미지 db에서 삭제
            fileCURD.deleteFile(useServiceName,reviewImage.getReviewimageid());
        }
        //매장 리뷰 카운트 추가, Null에러 반환
        Objects.requireNonNull(restaurant,"해당 매장을 찾을 수 없습니다.").setReviewcount(restaurant.getReviewcount()-1);
        reviewRepository.deleteById(reviewid);//리뷰 삭제
    }

    @Transactional(readOnly = true)
    public Page<ReviewAndReplyDto> getReview(String reviewid){ //알람에서 리뷰 하나만 조회할때(업주는 새로운 리뷰 알람의 링크, 사용자는 답글이 달렸을때의 알람 링크)
        Pageable pageable = PageRequest.of(0, 1);
        Page<Review> reviewPage = reviewRepository.findByReviewid(reviewid,pageable);

        Page<ReviewAndReplyDto> reviewDtos = reviewPage.map(review -> {
            ReviewAndReplyDto reviewAndReplyDto = modelMapper.map(review, ReviewAndReplyDto.class);// DTO변환 (주문 메뉴 목록을 포함하지 않음)
            if(review.getPayment()!=null){
                List<PaymentMenuDto> paymentMenus = paymentService.paymentMenusSet(review.getPayment().getPaymentid());//리뷰의 결제아이디를 가져와 해당 결제의 주문 메뉴 조회
                reviewAndReplyDto.setPaymentMenuDtos(paymentMenus);//조회한 주문 메뉴를 주입
            }
            //리뷰 이미지 가져오기
            List<String> imagelink = review.getReviewimages().stream()
                    .map(ReviewImage::getImagelink)
                    .collect(Collectors.toList());
            reviewAndReplyDto.setIamgeLinks(imagelink);
            //답글 여부 가져오기
            ReviewReply reviewReply = review.getReviewReply();
            if(reviewReply!=null){
                reviewAndReplyDto.setReviewReplyDto(modelMapper.map(reviewReply,ReviewReplyDto.class));
            }
            return reviewAndReplyDto;
        });
        return reviewDtos;
    }

    @Transactional(readOnly = true)
    public Page<ReviewAndReplyDto> getReviewAll(String restaurantid, int page, int pageSize, int scopecheck){ //전체리뷰(방문,포장) 조회
        //scopecheck에 int값이 들어가는 부분은 1=별점 및 날짜순, 2= 날짜순, 3=답글 작성안된 날짜순(내림차순)
        Pageable pageable = PageRequest.of(page - 1, pageSize);//기본페이지를 1로 두었기에 -1
        Page<Review> reviewPage;
        if(scopecheck==1){//1 = 별점 높은순
            //Payment의 레스토랑 객체의 레스토랑 아이디로 검색
            reviewPage = findReviewsByRestaurant(restaurantid,pageable,true,false);
        }else if(scopecheck==2){// 2= 별점 관계 없이
            //Payment의 레스토랑 객체의 레스토랑 아이디로 검색
            reviewPage = findReviewsByRestaurant(restaurantid,pageable,false,false);
        }else{//답글이 없는것만 조회
            reviewPage = findReviewsByRestaurant(restaurantid,pageable,false,true);
        }
        Page<ReviewAndReplyDto> reviewDtos = reviewPage.map(review -> {
            ReviewAndReplyDto reviewAndReplyDto = modelMapper.map(review, ReviewAndReplyDto.class);// DTO변환 (주문 메뉴 목록을 포함하지 않음)
            if(review.getPayment()!=null){
                List<PaymentMenuDto> paymentMenus = paymentService.paymentMenusSet(review.getPayment().getPaymentid());//리뷰의 결제아이디를 가져와 해당 결제의 주문 메뉴 조회
                reviewAndReplyDto.setPaymentMenuDtos(paymentMenus);//조회한 주문 메뉴를 주입
            }
            //리뷰 이미지 가져오기
            List<String> imagelink = review.getReviewimages().stream()
                    .map(ReviewImage::getImagelink)
                    .collect(Collectors.toList());
            reviewAndReplyDto.setIamgeLinks(imagelink);
            //답글 여부 가져오기
            ReviewReply reviewReply = review.getReviewReply();
            if(reviewReply!=null){
                reviewAndReplyDto.setReviewReplyDto(modelMapper.map(reviewReply,ReviewReplyDto.class));
            }
            return reviewAndReplyDto;
        });
        return reviewDtos;
    }
    @Transactional(readOnly = true)
    public Page<ReviewAndReplyDto> getReviewPayment(String restaurantid,int page, int pageSize, int scopecheck){ //포장 주문 리뷰 조회
        //scopecheck에 int값이 들어가는 부분은 1=별점 및 날짜순, 2= 날짜순, 3=답글 작성안된 날짜순(내림차순)
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("date")); //날짜기준 내림차순 정렬
        Pageable pageable;
        if (scopecheck==1) {//1일때 별점+날짜 기준
            sorts.add(Sort.Order.desc("scope"));//별점기준 내림차순
        }
        pageable = PageRequest.of(page - 1, pageSize);//기본페이지를 1로 두었기에 -1

        Page<Review> reviewPage;
        if(scopecheck==2){//2일때 날짜 기준만
            reviewPage= this.reviewRepository.findByPayment_Restaurant_Restaurantid(restaurantid,pageable);//Payment의 레스토랑 객체의 레스토랑 아이디로 검색
        }else{//3일때 답글 없는거만 조회(관리자용)
            reviewPage= this.reviewRepository.findByWithoutReplyAndPayment_Restaurantid(restaurantid,pageable);
        }
        Page<ReviewAndReplyDto> reviewDtos = reviewPage.map(review -> {
            ReviewAndReplyDto reviewAndReplyDto = modelMapper.map(review, ReviewAndReplyDto.class);// DTO변환 (주문 메뉴 목록을 포함하지 않음)
            List<PaymentMenuDto> paymentMenus = paymentService.paymentMenusSet(review.getPayment().getPaymentid());//리뷰의 결제아이디를 가져와 해당 결제의 주문 메뉴 조회
            reviewAndReplyDto.setPaymentMenuDtos(paymentMenus);//조회한 주문 메뉴를 주입

            //리뷰 이미지 가져오기
            List<String> imagelink = review.getReviewimages().stream()
                    .map(ReviewImage::getImagelink)
                    .collect(Collectors.toList());
            reviewAndReplyDto.setIamgeLinks(imagelink);
            //답글 여부 가져오기
            ReviewReply reviewReply = review.getReviewReply();
            if(reviewReply!=null){
                reviewAndReplyDto.setReviewReplyDto(modelMapper.map(reviewReply,ReviewReplyDto.class));
            }
            return reviewAndReplyDto;
        });
        return reviewDtos;
    }
    @Transactional(readOnly = true)
    public Page<ReviewAndReplyDto> getReviewVisit(String restaurantid,int page, int pageSize, int scopecheck){ // 방문 예약 리뷰 조회
        //scopecheck에 int값이 들어가는 부분은 1=별점 및 날짜순, 2= 날짜순, 3=답글 작성안된 날짜순(내림차순)
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("date")); //날짜기준 내림차순 정렬
        Pageable pageable;
        if (scopecheck==1) {
            sorts.add(Sort.Order.desc("scope"));//별점기준 내림차순
        }
        pageable = PageRequest.of(page - 1, pageSize);//기본페이지를 1로 두었기에 -1

        Page<Review> reviewPage;
        if(scopecheck==2){//2일때 날짜 기준만
            reviewPage= this.reviewRepository.findByVisit_Restaurant_Restaurantid(restaurantid,pageable);//Payment의 레스토랑 객체의 레스토랑 아이디로 검색
        }else{//3일때 답글 없는거만 조회(관리자용)
            reviewPage= this.reviewRepository.findByWithoutReplyAndVisit_Restaurantid(restaurantid,pageable);
        }
        Page<ReviewAndReplyDto> reviewDtos = reviewPage.map(review -> {
            ReviewAndReplyDto reviewAndReplyDto = modelMapper.map(review, ReviewAndReplyDto.class);// DTO변환 (주문 메뉴 목록을 포함하지 않음)

            //리뷰 이미지 가져오기
            List<String> imagelink = review.getReviewimages().stream()
                    .map(ReviewImage::getImagelink)
                    .collect(Collectors.toList());
            reviewAndReplyDto.setIamgeLinks(imagelink);
            //답글 여부 가져오기
            ReviewReply reviewReply = review.getReviewReply();
            if(reviewReply!=null){
                reviewAndReplyDto.setReviewReplyDto(modelMapper.map(reviewReply,ReviewReplyDto.class));
            }
            return reviewAndReplyDto;
        });
        return reviewDtos;
    }
    @Transactional(readOnly = true)
    public Page<ReviewAndReplyDto> getMyrestaurant(int page, int pageSize, int scopecheck){ //자신의 매장 리뷰 보기
        // 여기에 문자열 받아서 스위치문으로 값 불러오기 할까
        User user = getCurrentUser();
        Restaurant restaurant = restaurantRepository.findByUser(user);//현재 로그인한 유저의 매장이 있나 확인

        if(restaurant!=null) {
            return getReviewAll(restaurant.getRestaurantid(), page, pageSize,scopecheck);
        } else {
            // 매장이 없는 경우 처리
            return Page.empty(); // 빈 페이지 반환
        }
    }

    @Transactional(readOnly = true) //자신의 매장의 리뷰 (정렬) 조회(업주용)
    public Page<ReviewAndReplyDto> sortMyrestaurant(int page,int pageSize, String sort){
        User user = getCurrentUser();
        Restaurant restaurant = restaurantRepository.findByUser(user);//현재 로그인한 유저의 매장이 있나 확인

        if(restaurant!=null) {
            return switch (sort) {//scopecheck에 int값이 들어가는 부분은 1=별점 및 날짜순, 2= 날짜순, 3=답글 작성안된 날짜순(내림차순)
                case ("scope") ->//별점 높은 순과 날짜를 기준으로 리뷰 조회
                        getReviewAll(restaurant.getRestaurantid(), page, pageSize, 1);
                case ("visit") ->
                    //방문을 날짜 기준으로 조회, 별점순으로 조회도 가능하니 필요에 따라 추가하면됨
                        getReviewVisit(restaurant.getRestaurantid(), page, pageSize, 2);
                case ("visitReply") ->
                    //방문을 날짜 기준으로 조회 (답글이 없는것 조회)
                        getReviewVisit(restaurant.getRestaurantid(), page, pageSize, 3);
                case ("payment") ->
                    //포장을 날짜 기준으로 조회
                        getReviewPayment(restaurant.getRestaurantid(), page, pageSize, 2);
                case ("paymentReply") ->
                    //포장을 날짜 기준으로 조회 (답글이 없는것 조회)
                        getReviewPayment(restaurant.getRestaurantid(), page, pageSize, 3);
                case ("allReply") ->
                    //답글이 없는 포장,방문 조회
                        getReviewAll(restaurant.getRestaurantid(), page, pageSize, 3);
                default ->
                    //방문과 포장을 날짜 기준 조회
                        getReviewAll(restaurant.getRestaurantid(), page, pageSize, 2);
            };
        } else {
            // 매장이 없는 경우 처리
            return Page.empty(); // 빈 페이지 반환
        }
    }

    @Transactional
    public ReviewReply writeReply(String reviewid,ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewid).orElseThrow(
                ()-> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        User user = getCurrentUser();
        ReviewReply reviewReply = new ReviewReply();
        reviewReply.setReview(review);
        reviewReply.setUser(user);
        reviewReply.setDate(LocalDate.now());
        reviewReply.setContent(reviewDto.getContent());
        reviewReplyRepository.save(reviewReply);

        //알람을 해당 리뷰를 작성한 사용자에게 전송
        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setContent("리뷰에 답글이 달렸어요.");
        alarmDto.setUrl("api/user/mypage/review/"+reviewid);//사용자가 작성한 리뷰로 이동(답글포함)
        alarmService.wirteAlarm(alarmDto,"리뷰 답글",review.getUser());//리뷰를 작성했던 사용자에게 보내는 알람

        return reviewReply;
    }
    @Transactional
    public ReviewReply modifyReply(String replyid, ReviewDto reviewDto){
        ReviewReply reviewReply=reviewReplyRepository.findById(replyid).orElseThrow(
                ()-> new EntityNotFoundException("리뷰의 답글을 찾을 수 없습니다."));
        reviewReply.setContent(reviewDto.getContent());
        return  reviewReply;
    }
    @Transactional
    public void deleteReply(String replyid){
        reviewReplyRepository.deleteById(replyid);
    }
}
