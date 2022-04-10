package com.technova.shopping_cart.TechNova.Cart.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column
    private String categoryName;

    @Column
    private String createdBy;

}
