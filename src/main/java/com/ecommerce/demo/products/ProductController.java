package com.ecommerce.demo.products;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.categories.Category;
import com.ecommerce.demo.categories.CategoryRepository;
import com.ecommerce.demo.utils.customPage.CustomPage;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @JsonView(ProductView.Public.class)
    @GetMapping
    public CustomPage<Product> getAllDeletedProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Product> result = productRepository.findByIsDeleted(pageable, false);
        return new CustomPage<>(result, "/categories");
    }

    @JsonView(ProductView.WithDeleteDate.class)
    @GetMapping("/trash")
    public CustomPage<Product> getAllDeletedProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "false") Boolean isDeleted) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Product> result = productRepository.findByIsDeleted(pageable, true);
        return new CustomPage<>(result, "/categories");
    }

    @GetMapping("/{id}")
    @JsonView(ProductView.Public.class)
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PatchMapping("/{id}")
    @JsonView(ProductView.Public.class)
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> product) {
        Optional<Product> productToUpdate = productRepository.findById(id);
        if (productToUpdate.isPresent()) {
            Product productToSave = productToUpdate.get();

            if (product.containsKey("name")) {
                productToSave.setName((String) product.get("name"));
            }
            if (product.containsKey("description")) {
                productToSave.setDescription((String) product.get("description"));
            }
            if (product.containsKey("price")) {
                productToSave.setPrice((Double) product.get("price"));
            }
            if (product.containsKey("quantity")) {
                productToSave.setQuantity((Integer) product.get("quantity"));
            }
            if (product.containsKey("category")) {
                Map<String, Object> categoryToLink = (Map<String, Object>) product.get("category");
                Long categoryId = Long.parseLong((String) categoryToLink.get("id"));
                Optional<Category> categoryToUpdate = categoryRepository.findById(categoryId);
                if (categoryToUpdate.isPresent()) {
                    Category category = categoryToUpdate.get();
                    productToSave.setCategory(category);
                }
            }
            return productRepository.save(productToSave);
        }
        return null;
    }

    @JsonView(ProductView.Public.class)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
            @RequestParam(defaultValue = "false") Boolean isDeleted) {
        if (isDeleted) {
            productRepository.softDeleteProductById(id);
        } else {
            productRepository.deleteById(id);
        }
    }
}
