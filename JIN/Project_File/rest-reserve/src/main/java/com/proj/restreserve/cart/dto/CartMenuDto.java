package com.proj.restreserve.cart.dto;

import com.proj.restreserve.cart.entity.Cart;
import com.proj.restreserve.menu.entity.Menu;
import lombok.Data;

@Data
public class CartMenuDto {
    private Integer cartmenuid;
    private Cart cart;
    private Menu menu;
    private Integer menucount;
}
