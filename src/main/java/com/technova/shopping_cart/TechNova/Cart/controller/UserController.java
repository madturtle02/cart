package com.technova.shopping_cart.TechNova.Cart.controller;

import com.technova.shopping_cart.TechNova.Cart.Service.UserService;
import com.technova.shopping_cart.TechNova.Cart.dto.LoginRequest;
import com.technova.shopping_cart.TechNova.Cart.dto.UserRequest;
import com.technova.shopping_cart.TechNova.Cart.exception.UserNameNotFound;
import com.technova.shopping_cart.TechNova.Cart.model.User;
import com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/str")
    public String name(){
        return "Hello world";
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody @Valid UserRequest userRequest){
        Optional<User> user = userService.getUserByEmail(userRequest.getEmail());
        if(user.isPresent()){
            log.info("User with {} already exists.", user.get().getEmail());
            return ApiResponse.generateResponse(HttpStatus.ALREADY_REPORTED.value(),"User already exists.",null ,null);
        }
        userService.createUser(userRequest);
        return ApiResponse.generateResponse(HttpStatus.OK.value(),"User with email" + userRequest.getEmail() +" created successfully.",user ,null);

    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAll(@RequestParam(name="email",required = false) String email)throws UserNameNotFound{
        if(email!= null){
            Optional<User> user = userService.getUserByEmail(email);
            if(user.isPresent()){
                log.info("User with {} is fetched.",user.get().getEmail());
                return ApiResponse.generateResponse(HttpStatus.OK.value(),"user with email "+email+" fetched successfully.",user,null);
            }
//            throw new UserNameNotFound("User with email: " + email + " not found in our database.");
            return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"user with email "+email+" not found.",null,null);
        }else{
            List<User> users = userService.getAllUsers();
            if(users.size() > 0){
                return ApiResponse.generateResponse(HttpStatus.OK.value(),users.size()+" users available.",users,null);
            }
//            throw new UserNameNotFound("Users not available in database");
            return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"No users available.",null,"Users not found");
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") Long id )throws UserNameNotFound{
        Optional<User> existingUser = userService.getUser(id);
        if(existingUser.isPresent()){
            log.info("User with {} is fetched.",existingUser.get().getId());
            return ApiResponse.generateResponse(HttpStatus.OK.value(),"User fetched successfully", existingUser,null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"User with id "+id+" not found", null,"User not found.");
//        throw new UserNameNotFound("User with id: " + id + " not found in our database.");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest)throws UserNameNotFound{
        Optional<User> existingUser = userService.getUser(id);
        if(existingUser.isPresent()){
            log.info("User with {} is fetched.",existingUser.get().getId());
            userService.updateUser(existingUser.get(),userRequest);
            return ApiResponse.generateResponse(HttpStatus.OK.value(),"User updated successfully", existingUser,null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"User with id "+id+" not found", null,"User not found.");
//        throw new UserNameNotFound("User with id: " + id + " not found in our database.");

    }

    @DeleteMapping ("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) throws UserNameNotFound{
        Optional<User> existingUser = userService.getUser(id);
        if(existingUser.isPresent()){
            log.info("User with {} is fetched.",existingUser.get().getId());
            userService.deleteUser(existingUser.get());
            return ApiResponse.generateResponse(HttpStatus.OK.value(),"User deleted successfully", null,null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"User with id "+id+" not found", null,"User not found.");
//        throw new UserNameNotFound("User with id: " + id + " not found in our database.");
    }


    @PostMapping("/users/login")
    public ResponseEntity<Object> userLogin(@RequestBody @Valid LoginRequest loginRequest){
        UserDetails userDetails=userService.userLogin(loginRequest);
        return ApiResponse.generateResponse(HttpStatus.OK.value(),"Login Success full",userDetails,null);
    }

}
