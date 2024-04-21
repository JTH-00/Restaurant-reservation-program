package com.proj.restreserve.alarm.service;

import com.proj.restreserve.alarm.dto.AlarmDto;
import com.proj.restreserve.alarm.entity.Alarm;
import com.proj.restreserve.alarm.repository.AlarmRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }
    public Alarm wirteAlarm(AlarmDto alarmDto,User user){
        Alarm alarm = new Alarm();
        alarm.setDate(LocalDate.now());
        alarm.setUrl(alarmDto.getUrl());
        alarm.setContent(alarmDto.getContent());
        alarm.setUser(user);

        return alarm;
    }
    public Page<AlarmDto> selectAlarm(int page, int pageSize){
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Alarm> alarms =alarmRepository.findByUser(getCurrentUser(),pageable);
        Page<AlarmDto> alarmDtos = alarms.map(alarm
                -> modelMapper.map(alarm,AlarmDto.class));
        return alarmDtos;
    }
}
