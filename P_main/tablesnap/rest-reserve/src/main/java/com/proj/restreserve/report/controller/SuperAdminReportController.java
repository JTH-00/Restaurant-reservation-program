package com.proj.restreserve.report.controller;

import com.proj.restreserve.report.dto.ReportRestaurantDto;
import com.proj.restreserve.report.dto.ReportReviewDto;
import com.proj.restreserve.report.service.ReportService;
import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/superadmin")
public class SuperAdminReportController {
    private final ReportService reportService;
    private final RestaurantService restaurantService;
    @GetMapping("/reportrest/list")
    public ResponseEntity<Page<ReportRestaurantDto>> reportrestaurantlist(@RequestParam(required = false, defaultValue = "1") int page){
        Page<ReportRestaurantDto> reportRestaurantDtos = reportService.reportrestaurantAll(page,10);
        return ResponseEntity.ok(reportRestaurantDtos);
    }
    @GetMapping("/reportrest/list/{restaurantid}")
    public ResponseEntity<SelectRestaurantDto> reportrestaurantlist(@PathVariable String restaurantid){
        SelectRestaurantDto reportRestaurantDtos = restaurantService.findRestaurant(restaurantid).get();
        return ResponseEntity.ok(reportRestaurantDtos);
    }
    @GetMapping("/reportreview/list")
    public ResponseEntity<Page<ReportReviewDto>> reportreviewlist(@RequestParam(required = false, defaultValue = "1") int page){
        Page<ReportReviewDto> reportReviewDtos = reportService.reportreviewAll(page,10);
        return ResponseEntity.ok(reportReviewDtos);
    }
    @PostMapping("/reportrest/{reportrestaurantid}/confirm")
    public ResponseEntity<String> confirmReportRestaurant(@PathVariable String reportrestaurantid) {
        reportService.confirmReportRestaurant(reportrestaurantid);
        return ResponseEntity.ok("Reported restaurant confirmed successfully.");
    }

    @PostMapping("/reportrest/{restaurantid}/block")
    public ResponseEntity<String> blockRestaurant(@PathVariable String restaurantid,@RequestParam String reportrestaurantid) {
        reportService.blockRestaurant(restaurantid, reportrestaurantid);
        return ResponseEntity.ok("Restaurant blocked successfully.");
    }
        @PostMapping("/reportreview/{reportreviewid}/confirm")
    public ResponseEntity<String> confirmReportReview(@PathVariable String reportreviewid) {
        reportService.confirmReportReview(reportreviewid);
        return ResponseEntity.ok("Reported review confirmed successfully.");
    }
    @PostMapping("/reportreview/{userid}/block")
    public ResponseEntity<String> blockUser(@PathVariable String userid, @RequestParam String reportreviewid) {
        reportService.blockUser(userid,reportreviewid);
        return ResponseEntity.ok("Reported user blocked successfully.");
    }

    @PostMapping("/reportreview/{reportreviewid}/delete")
    public ResponseEntity<String> deleteReportRestaurant(@PathVariable String reportreviewid) {
        reportService.deleteReview(reportreviewid);
        return ResponseEntity.ok("Reported restaurant deleted successfully.");
    }

}
