package com.proj.restreserve.alarm.dto;

import com.proj.restreserve.detailpage.dto.DetailUserDto;
import com.proj.restreserve.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlarmDto {
    private String content;
    private String url;
}
