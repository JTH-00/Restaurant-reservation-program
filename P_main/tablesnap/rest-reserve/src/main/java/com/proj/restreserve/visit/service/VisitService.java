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
        Optional<Restaurant> restaurant = restaurantRepository.findById(visitDto.getRestaurant().getRestaurantid());
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
}
