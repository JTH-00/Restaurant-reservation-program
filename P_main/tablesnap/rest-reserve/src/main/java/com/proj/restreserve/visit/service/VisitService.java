package com.proj.restreserve.visit.service;

import com.proj.restreserve.alarm.dto.AlarmDto;
import com.proj.restreserve.alarm.service.AlarmService;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import com.proj.restreserve.visit.dto.VisitDto;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final RestaurantRepository restaurantRepository;
    private final AlarmService alarmService;
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }
    @Transactional
    public void reserveVisit(VisitDto visitDto){
        User user = getCurrentUser();

        Boolean existReserveVisit = visitRepository.existsByUserAndVisittime(user,visitDto.getVisittime());
        if(existReserveVisit){
            throw new IllegalStateException("Restaurant already reserve to same time");
        }
        Optional<Restaurant> restaurant = restaurantRepository.findById(visitDto.getRestaurant().getRestaurantid());
        if(restaurant.isEmpty()){
            throw new IllegalArgumentException("Restaurant not found");
        }else if(restaurant.get().getBan() || restaurant.get().getStopsales()){
            throw new IllegalArgumentException("해당 매장은 현재 영업 중이 아닙니다.");
        }
        String visitid= UUID.randomUUID().toString();

        Visit visit =new Visit();
        visit.setVisitid(visitid);
        visit.setVisittime(visitDto.getVisittime());
        visit.setUser(user);
        visit.setVisitcustomers(visitDto.getVisitcustomers());
        visit.setRestaurant(restaurant.get());
        //엔티티 클래스의 DynamicInsert로 visitcheck는 false로 설정
        this.visitRepository.save(visit);

        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setContent(user.getUsername()+"님이 해당 매장을 방문하시기로 했어요.");
        alarmDto.setUrl("api/admin/restaurant/reserve/refuse/"+visitid);// 업주에게 알람 리스트에 사용가능한 거절 url제공
        alarmService.wirteAlarm(alarmDto,"방문 예약",restaurant.get().getUser());//레스토랑 업주에게 보내는 알람
    }
    @Transactional(readOnly = true)
    public Page<Visit> showVisitReserve(int page, int pagesize){//방문 예약 신청 리스트
        Pageable pageable = PageRequest.of(page,pagesize);
        User user = getCurrentUser();
        Restaurant restaurant = restaurantRepository.findByUser(user);
        Page<Visit> visits;
        if(restaurant!=null){
            visits = visitRepository.findByVisitcheckFalseAndRestaurant(restaurant,pageable);
        }else{
            throw new RuntimeException("로그인한 유저의 매장정보가 없습니다.");
        }
        return visits;
    }

    @Transactional
    public void refuseVisit(String visitid){//방문 예약 거절
        Visit visit = visitRepository.getReferenceById(visitid);

        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setContent(visit.getUser().getUsername()+"님의 매장 방문이 거절되었어요.");
        alarmService.wirteAlarm(alarmDto,"방문 예약",visit.getUser());//예약을 신청한 사용자에게 보내는 알람

        visitRepository.deleteById(visitid);
    }

    @Transactional
    public void checkVisit(String visitid){//방문 예약 확인
        Visit visit = visitRepository.getReferenceById(visitid);
        visit.setVisitcheck(true);

        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setContent(visit.getUser().getUsername()+"님의 매장 방문이 확인되었어요.");
        alarmService.wirteAlarm(alarmDto,"방문 예약",visit.getUser());//예약을 신청한 사용자에게 보내는 알람
    }
}
