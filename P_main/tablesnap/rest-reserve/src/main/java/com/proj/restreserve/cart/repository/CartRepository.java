package com.proj.restreserve.cart.repository;

import com.proj.restreserve.cart.entity.Cart;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository <Cart,String> {
    Optional<Cart> findByUser(User user);

    Cart findByCartid(String cartid);
}
