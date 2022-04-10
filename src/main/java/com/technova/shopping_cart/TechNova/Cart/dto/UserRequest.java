package com.technova.shopping_cart.TechNova.Cart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    @NotBlank(message = "First Name should not be empty.")
    @Size(min = 3, max = 5, message = "Size must be between 3 to 5 characters. ")
    private String firstName;

    @NotBlank(message = "Last name should not be empty.")
    private String lastName;

    @NotBlank(message = "Email should not be empty.")
    @Email(message = "Should be a proper email.")
    private String email;

    @NotBlank(message = "Should not be blank")
    private String password;
}
