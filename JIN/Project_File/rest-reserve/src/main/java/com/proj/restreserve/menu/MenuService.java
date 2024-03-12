package com.proj.restreserve.menu;

import com.proj.restreserve.restaurant.Restaurant;
import com.proj.restreserve.restaurant.RestaurantDto;
import com.proj.restreserve.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;

    @Transactional(readOnly = true)
    public Map<String, List> findMenu(String restaurantid){
        Restaurant restaurant =  this.restaurantRepository.getReferenceById(restaurantid);
        List<Menu> menuList = this.menuRepository.findByRestaurantid(restaurant);

        Set<String> categoryList = new HashSet<>();//총 카테고리를 저장(메뉴를 조회하면서 카테고리를 확인 후 보관, 중복x)
        List<MenuDto> menuDtoList = new ArrayList<>();//총 메뉴 저장

        for (Menu menu : menuList) {
            MenuDto menuDto = modelMapper.map(menu, MenuDto.class);//DTO로 변환
            menuDtoList.add(menuDto);
            categoryList.add(menuDto.getCategoryid().getName());//카테고리 이름을 Set에 저장
        }
        Map<String, List> abc = new HashMap<>();//Map을 사용하여 Key값 설정과 리턴을 두개로 함
        abc.put("카테고리", new ArrayList<>(categoryList));
        abc.put("메뉴",menuDtoList);
        return abc;
    }
}
