package com.technova.shopping_cart.TechNova.Cart.repository;

import com.technova.shopping_cart.TechNova.Cart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
