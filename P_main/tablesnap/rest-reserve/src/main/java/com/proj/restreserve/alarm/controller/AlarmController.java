package com.proj.restreserve.alarm.controller;

import com.proj.restreserve.alarm.service.AlarmService;
import com.proj.restreserve.alarm.dto.AlarmDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
@Tag(name="Alarm", description = "알람 API")
public class AlarmController {
    private final AlarmService alarmService;
    @GetMapping("modal")
    @Operation(summary = "메인페이지 알람창", description = "최근 알람 5개를 보여줍니다.")
    public Page<AlarmDto> showAlarmModal(){//모달창으로 5개만 조회
        return alarmService.selectAlarm(1,5);
    }
    @GetMapping("alarmpage")
    @Operation(summary = "알람 리스트", description = "최신순을 기준으로 받은 알람의 리스트를 보여줍니다." +
            "파라미터로 현재 페이지 수를 받으며, 없을 시 1페이지로 고정됩니다.")
    public Page<AlarmDto> showAlarmPage(@RequestParam(required = false, defaultValue = "1") int page){//리스트 페이지 조회
        return alarmService.selectAlarm(page,10);
    }
}
