package com.ecommerce.demo.categories;

import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.utils.customPage.CustomPage;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @JsonView(CategoryView.Public.class)
    public CustomPage<Category> getAllCategories(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Category> result = categoryRepository.findAll(pageable);
        return new CustomPage<Category>(result, "/categories");
    }

    @GetMapping("/{id}")
    @JsonView(CategoryView.WithProducts.class)
    public Optional<Category> getCategoryById(
            @PathVariable Long id) {
        return categoryRepository.findById(id);
    }

    @PostMapping
    @JsonView(CategoryView.Public.class)
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PatchMapping("/{id}")
    @JsonView(CategoryView.Public.class)
    public Category updateCategory(
            @PathVariable Long id,
            @RequestBody Map<String, Object> category) {
        Optional<Category> categoryToUpdate = categoryRepository.findById(id);
        if (categoryToUpdate.isPresent()) {
            Category categoryToSave = categoryToUpdate.get();
            if (category.containsKey("name")) {
                categoryToSave.setName((String) category.get("name"));
            }
            if (category.containsKey("description")) {
                categoryToSave.setDescription((String) category.get("description"));
            }
            return categoryRepository.save(categoryToSave);
        }
        return null;
    }
}
