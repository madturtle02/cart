package com.technova.shopping_cart.TechNova.Cart.exception;

public class CategoryNotFound extends RuntimeException{
    String message;

    public CategoryNotFound(String message){
        super(message);
        this.message = message;
    }
}
