package com.proj.restreserve.visit.service;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import com.proj.restreserve.visit.dto.VisitDto;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
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
        Optional<Restaurant> restaurant = restaurantRepository.findById(visitDto.getRestaurantid());
        if(restaurant.isEmpty() || restaurant.get().getBan()){
            throw new IllegalArgumentException("Restaurant not found or banned");
        }
        Visit visit =new Visit();
        visit.setVisitid(UUID.randomUUID().toString());
        visit.setVisittime(visitDto.getVisittime());
        visit.setUser(user);
        visit.setVisitcustomers(visitDto.getVisitcustomers());
        visit.setRestaurant(restaurant.get());
        //엔티티 클래스의 DynamicInsert로 visitcheck는 false로 설정
        this.visitRepository.save(visit);
    }

    @Transactional(readOnly = true)
    public List<Visit> showVisitReserve(){//방문 예약 신청 리스트
        User user = getCurrentUser();
        Restaurant restaurant = restaurantRepository.findByUser(user);
        List<Visit> visits;
        if(restaurant!=null){
            visits = visitRepository.findByVisitcheckFalseAndRestaurant(restaurant);
        }else{
            throw new RuntimeException("로그인한 유저의 매장정보가 없습니다.");
        }
        return visits;
    }

    @Transactional(readOnly = true)
    public List<Visit> showPermitVisitReserve(){//수락한 방문 예약 리스트
        User user = getCurrentUser();
        Restaurant restaurant = restaurantRepository.findByUser(user);
        List<Visit> visits;
        if(restaurant!=null){
            LocalDateTime localDateTime= LocalDateTime.now().plusMinutes(30);//입장시간이 현재시간 + 30분 전의 예약한 방문 리스트 조회
            visits = visitRepository.findByVisitcheckTrueAndRestaurantAndVisittimeBefore(restaurant, localDateTime);
        }else{
            throw new RuntimeException("로그인한 유저의 매장정보가 없습니다.");
        }
        return visits;
    }
    @Transactional
    public void refuseVisit(String visitid){//방문 예약 거절
        //알람 작성해서 전송하는거 추가해야함
        visitRepository.deleteById(visitid);
    }
    
    @Transactional
    public void acceptVisit(String visitid){//방문 예약 수락
        Visit visit = visitRepository.getReferenceById(visitid);
        visit.setVisitcheck(true);
        //알람 보내기 해야함
    }
}
