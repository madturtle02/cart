package com.technova.shopping_cart.TechNova.Cart.Service;

import com.technova.shopping_cart.TechNova.Cart.dto.LoginRequest;
import com.technova.shopping_cart.TechNova.Cart.repository.UserRepository;
import com.technova.shopping_cart.TechNova.Cart.dto.UserRequest;
import com.technova.shopping_cart.TechNova.Cart.model.User;
import com.technova.shopping_cart.TechNova.Cart.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CartUserDetailsService cartUserDetailsService;

    public User createUser(UserRequest userRequest) {
        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setPassword(encodePassword(userRequest.getPassword()));
        return userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(User user, UserRequest userRequest){
        user.setLastName(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        user.setEmail(userRequest.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }


    public UserDetails userLogin(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            log.info("Authetication successful for: {}",loginRequest.getEmail());
        } catch (AuthenticationException e){
            log.info("exception occured in : {}",e.getMessage());
            throw new BadCredentialsException("Invalid credential") ;
            }
        UserDetails userDetails= cartUserDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtils.generateToken(userDetails);
        log.info("Token {}", token);
        System.out.println("Token: " +token);
        return userDetails;
        }


}
