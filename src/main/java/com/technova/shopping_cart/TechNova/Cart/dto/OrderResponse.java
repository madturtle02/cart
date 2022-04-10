package com.technova.shopping_cart.TechNova.Cart.dto;

import lombok.Data;

@Data
public class OrderResponse {
    private Object data;
    private String message;
    private Object errors;
    private Integer statusCode;
}
