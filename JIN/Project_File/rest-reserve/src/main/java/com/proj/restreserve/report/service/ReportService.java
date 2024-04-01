package com.proj.restreserve.report.service;

import com.proj.restreserve.detailpage.service.FileUpload;
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

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private final FileUpload fileUpload;

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
                    String fileName = uuid.toString() + "_" + file.getOriginalFilename().lastIndexOf(".");//uuid+확장자명으로 이름지정

                    String imageUrl = fileUpload.uploadImageToS3(file,"report/restaurant",fileName);//파일 업로드
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
                    String fileName = uuid.toString() + "_" + file.getOriginalFilename().lastIndexOf(".");//uuid+확장자명으로 이름지정

                    String imageUrl = fileUpload.uploadImageToS3(file,"report/review",fileName);//파일 업로드

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

}
