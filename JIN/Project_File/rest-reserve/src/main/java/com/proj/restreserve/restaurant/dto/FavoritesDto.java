package com.proj.restreserve.restaurant.dto;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import lombok.Data;

@Data
public class FavoritesDto {
    private String favoritesid;
    private User user;
    private Restaurant restaurant;
}
