package com.proj.restreserve.review.service;

import com.proj.restreserve.detailpage.service.FileUpload;
import com.proj.restreserve.payment.dto.PaymentMenuDto;
import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.repository.PaymentRepository;
import com.proj.restreserve.payment.service.PaymentService;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.dto.SelectReviewDto;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.review.entity.ReviewImage;
import com.proj.restreserve.review.repository.ReviewImageRepository;
import com.proj.restreserve.review.repository.ReviewRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private final FileUpload fileUpload;
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }

    @Transactional
    public Review writereview(ReviewDto reviewDto, List<MultipartFile> files) {
        // 리뷰 정보 저장
        Review review = new Review();
        // 사용자 인증 정보 가져오기
        User user = getCurrentUser();
        // 방문 정보 또는 결제 정보 가져오기
        Visit visit = null;
        Payment payment = null;

        if (reviewDto.getVisit() != null) {
            visit = visitRepository.findById(reviewDto.getVisit().getVisitid())
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 방문 정보가 없습니다."));
        } else if (reviewDto.getPayment() != null) {
            payment = paymentRepository.findById(reviewDto.getPayment().getPaymentid())
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 결제 정보가 없습니다."));
        }
        // 리뷰 작성
        review.setScope(reviewDto.getScope());
        review.setContent(reviewDto.getContent());
        review.setDate(LocalDate.now());
        review.setUser(user);
        review.setPayment(payment);
        review.setVisit(visit);

        List<ReviewImage> reviewImages = new ArrayList<>();

        // 각 파일에 대한 처리
        if (files != null) {
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString() + "_" + file.getOriginalFilename().lastIndexOf(".");//uuid+확장자명으로 이름지정

                    String imageUrl = fileUpload.uploadImageToS3(file,"review",fileName);//파일 업로드

                    // 리뷰 이미지 정보 생성
                    ReviewImage reviewImage = new ReviewImage();
                    reviewImage.setReviewimageid(uuid.toString());
                    reviewImage.setReview(review);
                    reviewImage.setImagelink(imageUrl);

                    // 이미지 정보 저장
                    reviewImages.add(reviewImage);
                }
            }
        }
        // 리뷰 정보 저장
        reviewRepository.save(review);
        // 리뷰 이미지 정보 저장
        for (ReviewImage reviewImage : reviewImages) {
            reviewImage.setReview(review); // 이미지 정보에 리뷰 정보 설정
            reviewImageRepository.save(reviewImage);
        }

        review.setReviewimages(reviewImages);
        reviewRepository.save(review); //이미지 링크를 출력하기 위해 다시 save

        return review;
    }
    public Page<SelectReviewDto> getReview(String restaurantid, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("Date")); //날짜기준 내림차순 정렬
        Pageable pageable = PageRequest.of(page-1, 5, Sort.by(sorts));//기본페이지를 1로 두었기에 -1, 5개의 리뷰

        Page<Review> reviewPage= this.reviewRepository.findByPayment_Restaurant_Restaurantid(restaurantid,pageable);//Payment의 레스토랑 객체의 레스토랑 아이디로 검색
        Page<SelectReviewDto> reviewDtos = reviewPage.map(review -> {
            SelectReviewDto selectReviewDto = modelMapper.map(review, SelectReviewDto.class);// DTO변환 (주문 메뉴 목록을 포함하지 않음)
            List<PaymentMenuDto> paymentMenus = paymentService.paymentMenusSet(review.getPayment().getPaymentid());//리뷰의 결제아이디를 가져와 해당 결제의 주문 메뉴 조회
            selectReviewDto.setPaymentMenuDtos(paymentMenus);//조회한 주문 메뉴를 주입

            List<String> imagelink = review.getReviewimages().stream()
                    .map(ReviewImage::getImagelink)
                    .collect(Collectors.toList());
            selectReviewDto.setIamgeLinks(imagelink);
            return selectReviewDto;
        });

        return reviewDtos;
    }
}
