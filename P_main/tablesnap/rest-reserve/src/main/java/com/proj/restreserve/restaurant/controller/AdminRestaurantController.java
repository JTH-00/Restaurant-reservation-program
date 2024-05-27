package com.proj.restreserve.restaurant.controller;

import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name= "AdminRestaurant", description = "업주 매장 등록 API")
public class AdminRestaurantController {

    private final RestaurantService restaurantService;
    @PostMapping(value = "/registration", consumes = {"multipart/form-data"})
    @Operation(summary = "업주의 매장 등록", description = "회원가입 이후 등록할 매장의 정보를 저장하여 관리자에게 신청합니다.<br>" +
            "RestaurantDto로 매장 정보를 입력하고, 사진을 같이 올려 저장합니다.<br>" +
            "최소 한장 이상의 사진을 요구합니다.<br>" +
            "해당 매장의 메뉴 리스트와 메뉴별 사진도 같이 저장합니다.<br>" +
            "저장된 내용은 관리자에게 접수되어 승인과 거부로 처리됩니다.<br>")
    public ResponseEntity<String> registrestaurant(
            @Valid @RequestPart("restaurantDto") RestaurantDto restaurantDto,
            @RequestPart(value = "files") List<MultipartFile> files,
            @RequestPart(value = "menuDtos") List<MenuDto> menuDtos,
            @RequestPart(value = "menuImageFiles") List<MultipartFile> menuImageFiles) {
        restaurantService.regist(restaurantDto, files, menuDtos, menuImageFiles);
        return ResponseEntity.ok("가게 및 메뉴 등록 성공");
    }
    @GetMapping(value = "/admin/checkpermit")
    @Operation(summary = "업주의 매장 승인여부 체크", description = "업주 넷바의 가게 관리 버튼을 눌렀을 때 사용됩니다.<br>" +
            "자신의 매장의 여부와 승인 체크의 따라서 상태메세지와 등장할 버튼의 이동경로를 바꾸는 용도입니다.<br>" +
            "가게관리와 매장 등록,수정 사이의 중간페이지로 사용될 예정입니다.<br>"+
            "매장이 조회가 안되면 생성, 승인여부 null일 경우는 승인요청중이라는 상태메세지,<br>" +
            "true와 false의 따라서 상태메세지와 가게 수정기능을 사용하게 됩니다.")
    public ResponseEntity<Restaurant> checkPermit() {//가게 관리, 가게 승인여부 체크하는 페이지
        return ResponseEntity.ok(restaurantService.checkPermit());
    }
}
