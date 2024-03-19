package com.proj.restreserve.restaurant.service;

import com.proj.restreserve.restaurant.dto.RestaurantDto;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.restaurant.entity.RestaurantImage;
import com.proj.restreserve.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Optional<RestaurantDto> findRestaurant(String restaurantid) {
        Restaurant restaurant = this.restaurantRepository.findByRestaurantid(restaurantid);
        //DTO변환
        RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);
        // 이미지 파일들의 링크 가져오기
        List<String> imageLinks = restaurant.getRestaurantimages().stream()
                .map(RestaurantImage::getImagelink)
                .collect(Collectors.toList());
        restaurantDto.setRestaurantimageLinks(imageLinks);
        return Optional.ofNullable(restaurantDto);
    }
}
