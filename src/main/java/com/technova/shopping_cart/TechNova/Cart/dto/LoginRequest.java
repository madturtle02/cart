package com.technova.shopping_cart.TechNova.Cart.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "Email should not be empty.")
    @Email(message = "Should be a proper email.")
    private String email;

    @NotBlank
    private String Password;
}
