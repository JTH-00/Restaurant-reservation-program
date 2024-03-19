package com.proj.restreserve.menu.service;

import com.proj.restreserve.category.dto.CategoryDto;
import com.proj.restreserve.menu.dto.MenuAndCategoryDto;
import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.menu.repository.MenuRepository;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final ModelMapper modelMapper;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    @Transactional(readOnly = true)
    public MenuAndCategoryDto findMenu(String restaurantid){
        Restaurant restaurant =  this.restaurantRepository.getReferenceById(restaurantid);
        List<Menu> menuList = this.menuRepository.findByRestaurantid(restaurant);

        Set<CategoryDto> categoryList  = new HashSet<>();//총 카테고리를 저장(메뉴를 조회하면서 카테고리를 확인 후 보관, 중복x)
        List<MenuDto> menuDtoList = new ArrayList<>();//총 메뉴 저장

        for (Menu menu : menuList) {
            MenuDto menuDto = modelMapper.map(menu, MenuDto.class);//DTO로 변환
            if(menu.getMenuimageid() !=null){
                menuDto.setImagelink(menu.getMenuimageid().getMenuimagelink().toString());
            }
            menuDtoList.add(menuDto);
            categoryList.add(menuDto.getCategoryid());//카테고리 이름을 Set에 저장
        }
        MenuAndCategoryDto MandCDTO = new MenuAndCategoryDto(categoryList,menuDtoList); //카테고리와 메뉴 담은 DTO
        return MandCDTO;
    }
}
