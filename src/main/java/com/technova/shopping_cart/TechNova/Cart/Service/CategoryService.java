package com.technova.shopping_cart.TechNova.Cart.Service;

import com.technova.shopping_cart.TechNova.Cart.repository.CategoryRepository;
import com.technova.shopping_cart.TechNova.Cart.dto.CategoryRequest;
import com.technova.shopping_cart.TechNova.Cart.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public Category createCategory(CategoryRequest categoryRequest) {
        Category newCategory = new Category();
        newCategory.setCategoryName(categoryRequest.getCategoryName());
        newCategory.setCreatedBy(categoryRequest.getCreatedBy());
        return repo.save(newCategory);
    }

    public void deleteCategory(Category category) {
        repo.delete(category);
    }

    public Optional<Category> getByCategoryName(String name) {
        return repo.findByCategoryName(name);
    }

    public Optional<Category> getByCategoryId(Long id) {
        return repo.findById(id);
    }

    public Category updateCategory(Category category, CategoryRequest categoryRequest) {
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCreatedBy(categoryRequest.getCreatedBy());
        return repo.save(category);
    }

    public List<Category> getAll() {
        return repo.findAll();
    }
}
