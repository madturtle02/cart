package com.technova.shopping_cart.TechNova.Cart.exception;

public class UserNameNotFound extends RuntimeException{
    private String message;

    public UserNameNotFound(String message){
        super(message);
        this.message = message;
    }
}
