package com.technova.shopping_cart.TechNova.Cart.Service;

import com.technova.shopping_cart.TechNova.Cart.model.User;
import com.technova.shopping_cart.TechNova.Cart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CartUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User with email:"+email+"not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),true,true,true,true,getAuthorized("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorized(String user) {
        return Collections.singleton(new SimpleGrantedAuthority(user));
    }
}
