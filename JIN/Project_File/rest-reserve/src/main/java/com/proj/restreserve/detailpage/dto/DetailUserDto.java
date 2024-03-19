package com.proj.restreserve.detailpage.dto;

import lombok.Data;

@Data
public class DetailUserDto { //가게 상세페이지의 업주 정보와 리뷰 작성자용 DTO
    private String username;
    private String phone;
}
