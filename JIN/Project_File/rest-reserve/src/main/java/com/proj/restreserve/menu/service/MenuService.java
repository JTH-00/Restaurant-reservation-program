package com.proj.restreserve.menu.service;

import com.proj.restreserve.menu.dto.MenuAndCategoryDto;
import com.proj.restreserve.menu.dto.SelectMenuDto;
import com.proj.restreserve.menu.entity.MenuImage;
import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.menu.repository.MenuRepository;
import com.proj.restreserve.menucategory.dto.MenuCategoryDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final ModelMapper modelMapper;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

/*    @Transactional
    public List<MenuDto> findMenu(String restaurantid){

        Restaurant restaurant= restaurantRepository.getReferenceById(restaurantid);
        List<Menu> menus = menuRepository.findByRestaurant(restaurant);

        return menus.stream().map(menu -> {
            MenuDto menuDto = new MenuDto();
            menuDto.setMenuid(menu.getMenuid());
            menuDto.setName(menu.getName());
            menuDto.setContent(menu.getContent());
            menuDto.setPrice(menu.getPrice());
            menuDto.setRestaurant(menu.getRestaurant());
            menuDto.setMenuCategory(menu.getMenuCategory());


            // 이미지 파일들의 정보 가져오기
            List<String> imageLinks = menu.getMenuimages().stream()
                    .map(MenuImage::getMenuimagelink)
                    .collect(Collectors.toList());
            menuDto.setImageLinks(imageLinks);

            return menuDto;
        }).collect(Collectors.toList());
    }*/
    @Transactional(readOnly = true)
    public MenuAndCategoryDto findMenu(String restaurantid){
        Restaurant restaurant =  this.restaurantRepository.getReferenceById(restaurantid);
        List<Menu> menuList = this.menuRepository.findByRestaurantAndDeletecheckFalse(restaurant);

        Set<MenuCategoryDto> categoryList  = new HashSet<>();//총 카테고리를 저장(메뉴를 조회하면서 카테고리를 확인 후 보관, 중복x)
        List<SelectMenuDto> selectMenuDtoList = new ArrayList<>();//총 메뉴 저장

        for (Menu menu : menuList) {
            SelectMenuDto selectMenuDto = modelMapper.map(menu, SelectMenuDto.class);//DTO로 변환
            if(menu.getMenuimages() !=null){
                selectMenuDto.setImagelink(menu.getMenuimages().getMenuimagelink().toString());
            }
            selectMenuDtoList.add(selectMenuDto);
            categoryList.add(selectMenuDto.getMenuCategory());//카테고리 이름을 Set에 저장
        }
        MenuAndCategoryDto MandCDTO = new MenuAndCategoryDto(categoryList, selectMenuDtoList); //카테고리와 메뉴 담은 DTO
        return MandCDTO;
    }
}
