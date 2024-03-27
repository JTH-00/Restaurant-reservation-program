package com.proj.restreserve.visit.service;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.service.MyPageService;
import com.proj.restreserve.visit.dto.VisitDto;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final MyPageService mypageService;
    private final VisitRepository visitRepository;
    private final RestaurantRepository restaurantRepository;
    @Transactional
    public void reserveVisit(VisitDto visitDto){
        User user = mypageService.getCurrentUser();

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
}
