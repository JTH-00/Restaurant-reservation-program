package com.proj.restreserve.report.controller;

import com.proj.restreserve.report.dto.ReportRestaurantDto;
import com.proj.restreserve.report.dto.ReportReviewDto;
import com.proj.restreserve.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/superadmin")
public class SuperAdminReportController {
    private final ReportService reportService;
    @GetMapping("/reportrest/list")
    public ResponseEntity<List<ReportRestaurantDto>> reportrestaurantlist(){
        List<ReportRestaurantDto> reportRestaurantDtos = reportService.reportrestaurantAll();
        return ResponseEntity.ok(reportRestaurantDtos);
    }

    @GetMapping("/reportreview/list")
    public ResponseEntity<List<ReportReviewDto>> reportreviewlist(){
        List<ReportReviewDto> reportReviewDtos = reportService.reportreviewAll();
        return ResponseEntity.ok(reportReviewDtos);
    }
    @PostMapping("/reportrest/{reportrestaurantid}/confirm")
    public ResponseEntity<String> confirmReportRestaurant(@PathVariable String reportrestaurantid) {
        reportService.confirmReportRestaurant(reportrestaurantid);
        return ResponseEntity.ok("Reported restaurant confirmed successfully.");
    }

    @PostMapping("/reportrest/{restaurantid}/block")
    public ResponseEntity<String> blockRestaurant(@PathVariable String restaurantid) {
        reportService.blockRestaurant(restaurantid);
        return ResponseEntity.ok("Restaurant blocked successfully.");
    }
    @PostMapping("/reportreview/{reportreviewid}/confirm")
    public ResponseEntity<String> confirmReportReview(@PathVariable String reportreviewid) {
        reportService.confirmReportReview(reportreviewid);
        return ResponseEntity.ok("Reported review confirmed successfully.");
    }
    @PostMapping("/reportreview/{userid}/block")
    public ResponseEntity<String> blockUser(@PathVariable String userid) {
        reportService.blockUser(userid);
        return ResponseEntity.ok("Reported user blocked successfully.");
    }

    @PostMapping("/reportreview/{reportreviewid}/delete")
    public ResponseEntity<String> deleteReportRestaurant(@PathVariable String reportreviewid) {
        reportService.deleteReview(reportreviewid);
        return ResponseEntity.ok("Reported restaurant deleted successfully.");
    }

}
