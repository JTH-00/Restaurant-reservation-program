package com.proj.restreserve.menu.repository;

import com.proj.restreserve.menu.entity.Menu;
import com.proj.restreserve.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,String>{
        @EntityGraph(attributePaths = {"menuimages"})
        List<Menu> findByRestaurant(Restaurant restaurant);
}
