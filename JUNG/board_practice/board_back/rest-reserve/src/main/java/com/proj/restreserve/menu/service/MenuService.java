package com.proj.restreserve.menu.service;

import com.proj.restreserve.menu.entity.MenuImage;
import com.proj.restreserve.menu.dto.MenuDto;
import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.menu.repository.MenuRepository;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
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
    }
}
