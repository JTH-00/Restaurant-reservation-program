package com.proj.restreserve.review.service;

import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.repository.PaymentRepository;
import com.proj.restreserve.review.dto.ReviewDto;
import com.proj.restreserve.review.entity.Review;
import com.proj.restreserve.review.entity.ReviewImage;
import com.proj.restreserve.review.repository.ReviewImageRepository;
import com.proj.restreserve.review.repository.ReviewRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.repository.VisitRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final VisitRepository visitRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public ReviewDto writereview(ReviewDto reviewDto, List<MultipartFile> files) {
        // 리뷰 정보 저장
        Review review = new Review();
        // 사용자 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useremail = authentication.getName();

        User user = userRepository.findByUseremail(useremail);
        // 방문 정보 또는 결제 정보 가져오기
        Visit visit = null;
        Payment payment = null;

        if (reviewDto.getVisitid() != null) {
            visit = visitRepository.findById(reviewDto.getVisitid())
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 방문 정보가 없습니다."));
        } else if (reviewDto.getPaymentid() != null) {
            payment = paymentRepository.findById(reviewDto.getPaymentid())
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 결제 정보가 없습니다."));
        }
        // 리뷰 작성
        review.setScope(reviewDto.getScope());
        review.setContent(reviewDto.getContent());
        review.setDate(LocalDate.now());
        review.setUser(user);
        review.setPayment(payment);
        review.setVisit(visit);

        // 리뷰 이미지 업로드 경로 설정
        String projectPath = System.getProperty("user.dir")+ File.separator + "rest-reserve" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" +  File.separator + "reviewfiles";

        List<ReviewImage> reviewImages = new ArrayList<>();

        // 각 파일에 대한 처리
        for (MultipartFile file : files) {
            // 이미지 파일이 비어있지 않으면 처리
            if (!file.isEmpty()) {
                try {
                    // 이미지 파일명 생성
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid.toString() + "_" + file.getOriginalFilename();

                    String reviewImageId = uuid.toString();
                    File saveFile = new File(projectPath, fileName);

                    // 파일 저장
                    // 랜덤 식별자와 파일명 지정(중복 방지)
                    file.transferTo(saveFile);

                    // 리뷰 이미지 정보 생성
                    ReviewImage reviewImage = new ReviewImage();
                    reviewImage.setReview(review);
                    reviewImage.setImagelink("images/" + fileName);

                    // 이미지 정보 저장
                    reviewImages.add(reviewImage);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 업로드 중 오류 발생: " + e.getMessage());
                }
            }
        }

        // 리뷰 정보 저장
        review = reviewRepository.save(review);

        // 리뷰 이미지 정보 저장
        reviewImages.forEach(reviewImageRepository::save);

        return convertToDto(review);
    }
    //Review -> Visit -> User 순환 참조가 발생하여 JSON 직렬화 과정에서 무한 루프 해결을 위해 Review 엔티티를 ReviewDto 데이터 전송 객체(DTO)로 변환하는 메서드 사용
    public ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setReviewid(review.getReviewid());
        dto.setScope(review.getScope());
        dto.setContent(review.getContent());
        dto.setDate(review.getDate());
        dto.setUserid(review.getUser().getUserid());
        if (review.getVisit() != null) dto.setVisitid(review.getVisit().getVisitid());
        if (review.getPayment() != null) dto.setPaymentid(review.getPayment().getPaymentid());
        List<String> imageLinks = review.getReviewimages().stream()
                .map(ReviewImage::getImagelink)
                .collect(Collectors.toList());
        dto.setImageLinks(imageLinks);
        return dto;
    }
}