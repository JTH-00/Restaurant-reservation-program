package com.proj.restreserve.restaurant.repository;

import com.proj.restreserve.restaurant.entity.Favorites;
import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites,String> {
    List<Favorites> findByUser(User user);

    boolean existsByUserAndRestaurant(User user, Restaurant restaurant);
}
