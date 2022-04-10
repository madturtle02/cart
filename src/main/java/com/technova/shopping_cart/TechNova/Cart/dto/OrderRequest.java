package com.technova.shopping_cart.TechNova.Cart.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderRequest {

    private String category;

    private Date date;

    private Long userId;

    private Long productId;


}
