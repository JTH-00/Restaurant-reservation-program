package com.proj.restreserve.report.controller;

import com.proj.restreserve.report.dto.ReportRestaurantDto;
import com.proj.restreserve.report.dto.ReportReviewDto;
import com.proj.restreserve.report.service.ReportService;
import com.proj.restreserve.restaurant.dto.SelectRestaurantDto;
import com.proj.restreserve.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/superadmin")
@Tag(name= "SuperAdminReport", description = "관리자 신고접수 관련 API")
public class SuperAdminReportController {
    private final ReportService reportService;
    private final RestaurantService restaurantService;
    @GetMapping("/reportrest/list")
    @Operation(summary = "신고받은 매장 조회", description = "최신순을 기준으로 신고받은 매장의 리스트를 보여줍니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<ReportRestaurantDto>> reportrestaurantlist(@RequestParam(required = false, defaultValue = "1") int page){
        Page<ReportRestaurantDto> reportRestaurantDtos = reportService.reportrestaurantAll(page,10);
        return ResponseEntity.ok(reportRestaurantDtos);
    }
    @GetMapping("/reportrest/list/{restaurantid}")
    @Operation(summary = "신고받은 매장 상세페이지 조회", description = "신고받은 매장의 상세 페이지를 보여줍니다.<br>" +
            "해당 매장의 id를 파라미터로 받습니다.")
    public ResponseEntity<SelectRestaurantDto> reportrestaurantlist(@PathVariable String restaurantid){
        SelectRestaurantDto reportRestaurantDtos = restaurantService.findRestaurant(restaurantid).get();
        return ResponseEntity.ok(reportRestaurantDtos);
    }
    @GetMapping("/reportreview/list")
    @Operation(summary = "신고받은 리뷰 조회", description = "최신순을 기준으로 신고받은 리뷰의 리스트를 보여줍니다.<br>" +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public ResponseEntity<Page<ReportReviewDto>> reportreviewlist(@RequestParam(required = false, defaultValue = "1") int page){
        Page<ReportReviewDto> reportReviewDtos = reportService.reportreviewAll(page,10);
        return ResponseEntity.ok(reportReviewDtos);
    }
    @PostMapping("/reportrest/{reportrestaurantid}/confirm")
    @Operation(summary = "신고받은 매장 확인", description = "확인 처리를 하여 별도의 재제를 가하지않고 리스트에 조회되지 않도록합니다.<br>" +
            "파라미터로 해당 신고의 id를 받습니다.")
    public ResponseEntity<String> confirmReportRestaurant(@PathVariable String reportrestaurantid) {
        reportService.confirmReportRestaurant(reportrestaurantid);
        return ResponseEntity.ok("Reported restaurant confirmed successfully.");
    }

    @PostMapping("/reportrest/{restaurantid}/block")
    @Operation(summary = "신고받은 매장 차단", description = "차단 처리를 하여 해당 매장을 차단처리(조회x, 주문불가)를 하며,<br>" +
            "신고접수 리스트에 조회되지 않도록합니다.<br>" +
            "파라미터로 해당 신고의 id와 신고받은 매장의 id를 받습니다.")
    public ResponseEntity<String> blockRestaurant(@PathVariable String restaurantid,@RequestParam String reportrestaurantid) {
        reportService.blockRestaurant(restaurantid, reportrestaurantid);
        return ResponseEntity.ok("Restaurant blocked successfully.");
    }
    @PostMapping("/reportreview/{reportreviewid}/confirm")
    @Operation(summary = "신고받은 리뷰 확인", description = "확인 처리를 하여 별도의 재제를 가하지않고 리스트에 조회되지 않도록합니다.<br>" +
            "파라미터로 해당 신고의 id를 받습니다.")
    public ResponseEntity<String> confirmReportReview(@PathVariable String reportreviewid) {
        reportService.confirmReportReview(reportreviewid);
        return ResponseEntity.ok("Reported review confirmed successfully.");
    }
    @PostMapping("/reportreview/{userid}/block")
    @Operation(summary = "신고받은 리뷰 유저 차단", description = "차단 처리를 하여 이용을 불가하게 하며,<br>" +
            "신고접수 리스트에 조회되지 않도록합니다.<br>" +
            "파라미터로 해당 신고의 id와 신고받은 유저의 id를 받습니다.")
    public ResponseEntity<String> blockUser(@PathVariable String userid, @RequestParam String reportreviewid) {
        reportService.blockUser(userid,reportreviewid);
        return ResponseEntity.ok("Reported user blocked successfully.");
    }

    @DeleteMapping("/reportreview/{reportreviewid}")
    @Operation(summary = "신고받은 리뷰 삭제", description = "해당 리뷰를 삭제합니다.<br>" +
            "신고접수 리스트에 조회되지 않도록합니다.<br>" +
            "파라미터로 해당 신고 id를 받습니다.")
    public ResponseEntity<String> deleteReportRestaurant(@PathVariable String reportreviewid) {
        reportService.deleteReview(reportreviewid);
        return ResponseEntity.ok("Reported restaurant deleted successfully.");
    }

}
