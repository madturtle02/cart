package com.technova.shopping_cart.TechNova.Cart.config;

import com.technova.shopping_cart.TechNova.Cart.Service.CartUserDetailsService;
import com.technova.shopping_cart.TechNova.Cart.utils.JwtFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilters jwtFilters;

    @Autowired
    private CartUserDetailsService cartUserDetailsService;

    @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(cartUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/api/users/**").permitAll().and().authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(jwtFilters, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    PasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBuilderBean() throws Exception {
       return super.authenticationManagerBean();
    }

}
