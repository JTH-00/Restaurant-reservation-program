package com.proj.restreserve.restaurant.service;

import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.entity.RestaurantImage;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public Optional<RestaurantDto> findRestaurant(String restaurantid) {//레스토랑 상세페이지 조회
        Restaurant restaurant = this.restaurantRepository.findByRestaurantidAndBanFalse(restaurantid);//밴이 안된 레스토랑 매장 조회
        //DTO변환
        RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);
        // 이미지 파일들의 링크 가져오기
        List<String> imageLinks = restaurant.getRestaurantimages().stream()
                .map(RestaurantImage::getImagelink)
                .collect(Collectors.toList());
        restaurantDto.setRestaurantimageLinks(imageLinks);
        return Optional.ofNullable(restaurantDto);
    }
    @Transactional(readOnly = true)
    public List<RestaurantDto> ShowRestaurantByreview(){//메인페이지 카테고리별 매장 총 8개 조회
        String list[] = {"족발,보쌈","찜,탕,찌개","치킨","카페,디저트","고기,구이","중식","버거", "돈까스,회,일식","양식","백반,죽,국수","분식","피자","아시안"};
        List<String> categories = Arrays.asList(list);
        Collections.shuffle(categories,new Random()); //셔플 이후 매장 고르기

        List<RestaurantDto> restaurantDtos = new ArrayList<>(); // 랜덤으로 선택된 카테고리 매장들
        for(int i=0; i< Math.min(8, categories.size()); i++) {//8개 이하로 출력
/*            String selected = ArrayList.get(i);
            List<Restaurant> restaurants = this.restaurantRepository.findManyReviewByCategory(selected); //리뷰 많은 순으로 출력
            Restaurant restaurant;

            if (!restaurants.isEmpty()) {
                restaurant = restaurants.get(0);// 리뷰가 제일 많은 매장 선택
            } else {
                continue;
            }
            RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);
            restaurantDtos.add(restaurantDto);*/
// 둘중 성능 보고 지울예정
            Query query = entityManager.createQuery(
                            "SELECT r FROM Restaurant r WHERE r.category = :category ORDER BY r.reviewcount DESC", Restaurant.class)
                    .setParameter("category", categories.get(i))
                    .setMaxResults(1); // 결과를 하나만 가져오도록 설정

            List<Restaurant> restaurants = query.getResultList();
            Restaurant restaurant;

            if (!restaurants.isEmpty()) {
                restaurant = restaurants.get(0);
            } else {
                continue;
            }
            RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);
            restaurantDtos.add(restaurantDto);
        }
        List<List<RestaurantDto>> mainPageRestaurant = new ArrayList<>(); //메인페이지에 출력할 용도
        mainPageRestaurant.add(restaurantDtos);
        return restaurantDtos;
    }
    @Transactional(readOnly = true)
    public List<RestaurantDto> showRestaurantByRandom(){//메인페이지 랜덤 8개 매장 조회
        List<Restaurant> random8Restaurants = this.restaurantRepository.findRandom8Restaurants();//랜덤 8개 조회
        List<RestaurantDto> random8RestaurantDtos = new ArrayList<>(); //Dto변환 후 넣을 리스트
        
        random8Restaurants.forEach(restaurant -> {//dto변환
            RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);
            random8RestaurantDtos.add(restaurantDto);
        });
        return random8RestaurantDtos;
    }
    @Transactional(readOnly = true)
    public List<List<RestaurantDto>> showMainPage(){//메인페이지 조회 목록합치기
        List<RestaurantDto> random8Restaurants = showRestaurantByRandom();//랜덤 8개 매장 조회
        List<RestaurantDto> restaurantByReview = ShowRestaurantByreview();//카테고리별 리뷰 많은 순의 매장 8개 조회

        List<List<RestaurantDto>> mainPage = new ArrayList<>(); //하나로 출력하기 위해서 추가로 리스트 선언
        mainPage.add(random8Restaurants);
        mainPage.add(restaurantByReview);

        return mainPage;
    }
}
