package com.proj.restreserve.menu;

import com.proj.restreserve.category.CategoryDto;
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
    public MenuAndCategoryDto findMenu(String restaurantid){
        Restaurant restaurant =  this.restaurantRepository.getReferenceById(restaurantid);
        List<Menu> menuList = this.menuRepository.findByRestaurantid(restaurant);

        Set<CategoryDto> categoryList  = new HashSet<>();//총 카테고리를 저장(메뉴를 조회하면서 카테고리를 확인 후 보관, 중복x)
        List<MenuDto> menuDtoList = new ArrayList<>();//총 메뉴 저장

        for (Menu menu : menuList) {
            MenuDto menuDto = modelMapper.map(menu, MenuDto.class);//DTO로 변환
            menuDtoList.add(menuDto);
            categoryList.add(menuDto.getCategoryid());//카테고리 이름을 Set에 저장
        }
        MenuAndCategoryDto MandCDTO = new MenuAndCategoryDto(categoryList,menuDtoList); //카테고리와 메뉴 담은 DTO
        return MandCDTO;
    }
}
