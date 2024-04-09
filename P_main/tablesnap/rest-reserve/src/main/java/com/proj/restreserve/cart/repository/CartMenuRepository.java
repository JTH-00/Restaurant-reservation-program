package com.proj.restreserve.cart.repository;

import com.proj.restreserve.cart.entity.Cart;
import com.proj.restreserve.cart.entity.CartMenu;
import com.proj.restreserve.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartMenuRepository extends JpaRepository<CartMenu,Integer> {
    boolean existsByCart_Cartid(String cartid);

    List<CartMenu> findByCart(Cart cart);

    Optional<CartMenu> findByCartAndMenu(Cart cart, Menu menu);
}
