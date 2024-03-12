package com.proj.restreserve.restaurant;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;
    @Transactional(readOnly = true)
    public Optional<RestaurantDto> findRestaurant(String restaurantid){
        //DTO변환
        RestaurantDto restaurantDto = modelMapper.map(this.restaurantRepository.getReferenceById(restaurantid),RestaurantDto.class);
        return Optional.ofNullable(restaurantDto);
    }
}
