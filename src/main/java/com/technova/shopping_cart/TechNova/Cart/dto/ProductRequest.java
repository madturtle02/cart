package com.technova.shopping_cart.TechNova.Cart.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProductRequest {

    @NotBlank
    @Size(min=3,max=7,message = "Size must be in between 3 and 7.")
    private String name;

    @NotBlank
    private String category;

    private Double unitPrice;

    private int quantityAvailable;
}

