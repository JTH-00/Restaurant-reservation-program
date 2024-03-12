package com.proj.restreserve.menu;

import com.proj.restreserve.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu,String>{
        List<Menu> findByRestaurantid(Restaurant restaurantid);
}
