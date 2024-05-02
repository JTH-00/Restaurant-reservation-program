package com.proj.restreserve.alarm.controller;

import com.proj.restreserve.alarm.service.AlarmService;
import com.proj.restreserve.alarm.dto.AlarmDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;
    @GetMapping("modal")
    public Page<AlarmDto> showAlarmModal(){//모달창으로 5개만 조회
        return alarmService.selectAlarm(1,5);
    }
    @GetMapping("alarmpage")
    public Page<AlarmDto> showAlarmPage(@RequestParam(required = false, defaultValue = "1") int page){//리스트 페이지 조회
        return alarmService.selectAlarm(page,10);
    }
}
