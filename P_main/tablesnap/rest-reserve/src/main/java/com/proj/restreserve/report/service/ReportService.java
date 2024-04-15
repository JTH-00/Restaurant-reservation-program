package com.proj.restreserve.report.service;

import com.proj.restreserve.detailpage.service.FileCURD;
import com.proj.restreserve.report.dto.ReportRestaurantDto;
import com.proj.restreserve.report.dto.ReportReviewDto;
import com.proj.restreserve.report.entity.ReportRestaurant;
import com.proj.restreserve.report.entity.ReportRestaurantImage;
import com.proj.restreserve.report.entity.ReportReview;
import com.proj.restreserve.report.entity.ReportReviewImage;
import com.proj.restreserve.report.repository.ReportRestaurantImageRepository;
import com.proj.restreserve.report.repository.ReportRestaurantRepository;
import com.proj.restreserve.report.repository.ReportReviewImageRepository;
import com.proj.restreserve.report.repository.ReportReviewRepository;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.review.repository.ReviewRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final ReportRestaurantRepository reportRestaurantRepository;
    private final ReportRestaurantImageRepository reportRestaurantImageRepository;
    private final ReportReviewRepository reportReviewRepository;
    private final ReportReviewImageRepository reportReviewImageRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final FileCURD fileCURD;
    private final List<String> useServiceName = Arrays.asList("report/restaurant","report/review");//S3버킷 폴더명

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }

    @Transactional
    public ReportRestaurant reportRestaurant(ReportRestaurantDto reportRestaurantDto, List<MultipartFile> files, String restaurantid) {
        // 리뷰 정보 저장
        ReportRestaurant reportRestaurant = new ReportRestaurant();
        // 사용자 인증 정보 가져오기
        User user = getCurrentUser();

        Restaurant restaurant = restaurantRepository.findById(restaurantid).orElseThrow(() -> new EntityNotFoundException("레스토랑을 찾을 수 없습니다."));

        // 리뷰 작성
        reportRestaurant.setContent(reportRestaurantDto.getContent());
        reportRestaurant.setDate(LocalDate.now());
        reportRestaurant.setReportrestcheck("미확인");
        reportRestaurant.setUser(user);
        reportRestaurant.setRestaurant(restaurant);

        List<ReportRestaurantImage> reportRestaurantImages = new ArrayList<>();

        // 각 파일에 대한 처리
        if (files != null) {
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString();

                    String imageUrl = fileCURD.uploadImageToS3(file,useServiceName.get(0),fileName);//파일 업로드 파일,파일폴더명,파일일련번호
                    // 리뷰 이미지 정보 생성
                    ReportRestaurantImage reportRestaurantImage = new ReportRestaurantImage();
                    reportRestaurantImage.setReportRestaurant(reportRestaurant);
                    reportRestaurantImage.setImagelink(imageUrl);

                    // 이미지 정보 저장
                    reportRestaurantImages.add(reportRestaurantImage);
                }
            }
        }

        // 리뷰 정보 저장
        reportRestaurantRepository.save(reportRestaurant);

        // 리뷰 이미지 정보 저장
        for (ReportRestaurantImage reportRestaurantImage : reportRestaurantImages) {
            reportRestaurantImage.setReportRestaurant(reportRestaurant); // 이미지 정보에 리뷰 정보 설정
            reportRestaurantImageRepository.save(reportRestaurantImage);
        }

        reportRestaurant.setReportrestaurantimages(reportRestaurantImages);
        reportRestaurantRepository.save(reportRestaurant);//이미지 링크를 출력하기 위해 다시 save

        return reportRestaurant;
    }

    @Transactional
    public ReportReview reportReview(ReportReviewDto reportReviewDto, List<MultipartFile> files, String reviewid) {
        // 리뷰 정보 저장
        ReportReview reportReview = new ReportReview();
        // 사용자 인증 정보 가져오기
        User user=getCurrentUser();

        Review review = reviewRepository.findById(reviewid).orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));

        // 리뷰 작성
        reportReview.setContent(reportReviewDto.getContent());
        reportReview.setDate(LocalDate.now());
        reportReview.setReportreviewcheck("미확인");
        reportReview.setUser(user);
        reportReview.setReview(review);

        List<ReportReviewImage> reportReviewImages = new ArrayList<>();

        // 각 파일에 대한 처리
        if (files != null) {
            for (MultipartFile file : files) {
                // 이미지 파일이 비어있지 않으면 처리
                if (!file.isEmpty()) {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString();

                    String imageUrl = fileCURD.uploadImageToS3(file,useServiceName.get(1),fileName);//파일 업로드 파일,파일폴더명,파일일련번호

                    // 리뷰 이미지 정보 생성
                    ReportReviewImage reportReviewImage = new ReportReviewImage();
                    reportReviewImage.setReportReview(reportReview);
                    reportReviewImage.setImagelink(imageUrl);

                    // 이미지 정보 저장
                    reportReviewImages.add(reportReviewImage);
                }
            }
        }

        // 리뷰 정보 저장
        reportReviewRepository.save(reportReview);

        // 리뷰 이미지 정보 저장
        for (ReportReviewImage reportReviewImage : reportReviewImages) {
            reportReviewImage.setReportReview(reportReview); // 이미지 정보에 리뷰 정보 설정
            reportReviewImageRepository.save(reportReviewImage);
        }

        reportReview.setReportreviewimages(reportReviewImages);
        reportReviewRepository.save(reportReview);//이미지 링크를 출력하기 위해 다시 save

        return reportReview;
    }
    public List<ReportRestaurantDto> reportrestaurantAll() {
        List<ReportRestaurant> reportrestaurants = reportRestaurantRepository.findAll();
        return reportrestaurants.stream().map(reportRestaurant -> {
            ReportRestaurantDto reportRestaurantDto = new ReportRestaurantDto();
            reportRestaurantDto.setRestaurant(reportRestaurant.getRestaurant());
            reportRestaurantDto.setContent(reportRestaurant.getContent());
            reportRestaurantDto.setDate(reportRestaurant.getDate());
            reportRestaurantDto.setUser(reportRestaurant.getUser());
            reportRestaurantDto.setReportrestcheck(reportRestaurant.getReportrestcheck());

            // 이미지 파일들의 정보 가져오기
            List<String> imageLinks = reportRestaurant.getReportrestaurantimages().stream()
                    .map(ReportRestaurantImage::getImagelink)
                    .collect(Collectors.toList());
            reportRestaurantDto.setReportrestaurantimages(imageLinks);

            return reportRestaurantDto;
        }).collect(Collectors.toList());
    }
    public List<ReportReviewDto> reportreviewAll() {
        List<ReportReview> reportreviews = reportReviewRepository.findAll();
        return reportreviews.stream().map(reportReview -> {
            ReportReviewDto reportReviewDto = new ReportReviewDto();
            reportReviewDto.setReview(reportReview.getReview());
            reportReviewDto.setContent(reportReview.getContent());
            reportReviewDto.setDate(reportReview.getDate());
            reportReviewDto.setUser(reportReview.getUser());
            reportReviewDto.setReportreviewcheck(reportReview.getReportreviewcheck());

            // 이미지 파일들의 정보 가져오기
            List<String> imageLinks = reportReview.getReportreviewimages().stream()
                    .map(ReportReviewImage::getImagelink)
                    .collect(Collectors.toList());
            reportReviewDto.setReportreviewimages(imageLinks);

            return reportReviewDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void confirmReportRestaurant(String reportrestaurantid) {
        ReportRestaurant reportRestaurant = reportRestaurantRepository.findById(reportrestaurantid)
                .orElseThrow(() -> new IllegalArgumentException("Reported restaurant not found"));
        reportRestaurant.setReportrestcheck("확인");
        reportRestaurantRepository.save(reportRestaurant);
    }

    @Transactional
    public void blockRestaurant(String restaurantid) {
        Restaurant restaurant = restaurantRepository.findById(restaurantid)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        restaurant.setBan(true);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void confirmReportReview(String reportreviewid) {
        ReportReview reportReview = reportReviewRepository.findById(reportreviewid)
                .orElseThrow(() -> new IllegalArgumentException("Reported review not found"));
        reportReview.setReportreviewcheck("확인");
        reportReviewRepository.save(reportReview);
    }

    @Transactional
    public void deleteReview(String reportReviewid) {
        // 로직: 해당 ID를 가진 리뷰를 삭제합니다.
        ReportReview reportReview = reportReviewRepository.findById(reportReviewid)
                .orElseThrow(() -> new IllegalArgumentException("Reported review not found"));

        // Review가 null이 아닌 경우 해당 Review와 연관된 모든 reportreview를 가져와서 삭제합니다.
        Review review = reportReview.getReview();
        if (review != null) {
            List<ReportReview> reportReviews = reportReviewRepository.findByReviewReviewid(review.getReviewid());
            for (ReportReview rr : reportReviews) {
                reportReviewRepository.deleteById(rr.getReportreviewid());
            }
        }

        // Review를 삭제합니다.
        reviewRepository.deleteById(review.getReviewid());
    }
    @Transactional
    public void blockUser(String userid) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new IllegalArgumentException("User not found with "));
        user.setBan(true);
        userRepository.save(user);
    }

}
