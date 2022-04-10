package com.technova.shopping_cart.TechNova.Cart.controller;

import com.technova.shopping_cart.TechNova.Cart.Service.CategoryService;
import com.technova.shopping_cart.TechNova.Cart.dto.CategoryRequest;
import com.technova.shopping_cart.TechNova.Cart.model.Category;
import com.technova.shopping_cart.TechNova.Cart.repository.CategoryRepository;
import com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryRepository repo;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public Category createCategory(@RequestBody CategoryRequest categoryRequest){
        Category savedCategory = categoryService.createCategory(categoryRequest);
        return savedCategory;
    }

    @GetMapping("/category")
    public ResponseEntity getAllCategory(){
        List<Category> categories= categoryService.getAll();
        if(categories.size()>0){
            return ApiResponse.generateResponse(HttpStatus.OK.value(),"Category fetched successfully", categories,null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"Category not found", null,"Order not found.");
    }

    @GetMapping("/category/name")
    public ResponseEntity getByCategoryName(@RequestParam(name="name",required = true) String name){
        Optional<Category> existingCategory= categoryService.getByCategoryName(name);
        if(existingCategory.isPresent()){
            log.info("Category with {} is fetched.",existingCategory.get().getCategoryId());
            return ApiResponse.generateResponse(HttpStatus.OK.value(),"Category fetched successfully", existingCategory,null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"Category with name "+name+" not found", null,"Category not found.");
//        throw new CategoryNotFound("Category with name "+ name + " not found.");
    }

    @GetMapping("/category/id")
    public ResponseEntity getBycategoryId(@RequestParam(name="id",required = true) Long id){
        Optional<Category> existingCategory= categoryService.getByCategoryId(id);
        if(existingCategory.isPresent()){
            log.info("User with {} is fetched.",existingCategory.get().getCategoryId());
            return ApiResponse.generateResponse(HttpStatus.OK.value(),"Category fetched successfully", existingCategory,null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"Category with id "+id+" not found", null,"Category not found.");
    }


    @PutMapping("/category/{id}")
    public ResponseEntity updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        Optional<Category> existingCategory = categoryService.getByCategoryId(id);
        if(existingCategory.isPresent()){
            log.info("User with {} is fetched.",existingCategory.get().getCategoryId());
            categoryService.updateCategory(existingCategory.get(),categoryRequest);
            return ApiResponse.generateResponse(HttpStatus.OK.value(),"Category updated successfully", existingCategory,null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"Category with id "+id+" not found", null,"Category not found.");
//        throw new CategoryNotFound("Category with id "+ id + " not found.");
    }

    @DeleteMapping ("/category/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id){
        Optional<Category> existingCategory = categoryService.getByCategoryId(id);
        if(existingCategory.isPresent()){
            log.info("User with {} is fetched.",existingCategory.get().getCategoryId());
            categoryService.deleteCategory(existingCategory.get());
            return ApiResponse.generateResponse(HttpStatus.OK.value(),"Category deleted successfully", null,null);
        }
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(),"Category with id "+id+" not found", null,"Category not found.");
//        throw new CategoryNotFound("Category with id "+ id + " not found.");
    }

}
