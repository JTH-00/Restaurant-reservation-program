package com.proj.restreserve.cart.dto;

import com.proj.restreserve.cart.entity.CartMenu;
import com.proj.restreserve.user.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private String cartid;
    private User user;
    private List<CartMenu> cartMenus;
    private double totalAmount;
    public CartDto(List<CartMenu> cartMenus, double totalAmount) {
        this.cartMenus = cartMenus;
        this.totalAmount = totalAmount;
    }
}
