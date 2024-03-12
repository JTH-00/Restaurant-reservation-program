package com.proj.restreserve.pageload;

import com.proj.restreserve.menu.CategoryDto;
import com.proj.restreserve.menu.MenuDto;
import com.proj.restreserve.menu.MenuService;
import com.proj.restreserve.restaurant.Restaurant;
import com.proj.restreserve.restaurant.RestaurantDto;
import com.proj.restreserve.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageloadService {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    @Transactional(readOnly = true)
    public Map<String,Object> pageload(String restaurantid){ //DTO를 모아서 한번에 출력하기 위해 Map사용
        RestaurantDto date1= restaurantService.findRestaurant(restaurantid).get();//레스토랑 정보
        Map<String,List> date2= menuService.findMenu(restaurantid);//카테고리 종류와 총 메뉴를 Map으로 가져오기

        Map<String,Object> objectMap = new HashMap<>();

        objectMap.put("restaurant",date1); //레스토랑 정보
        objectMap.put("category",date2.get("카테고리"));//설정한 키값으로 불러오기
        objectMap.put("menu",date2.get("메뉴"));
        return objectMap;
    }
}
