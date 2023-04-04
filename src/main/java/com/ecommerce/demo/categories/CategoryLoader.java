package com.ecommerce.demo.categories;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import com.ecommerce.demo.DemoApplication;
import com.ecommerce.demo.products.Product;
import com.ecommerce.demo.products.ProductFaker;
import com.ecommerce.demo.products.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CategoryLoader implements CommandLineRunner {

    int count;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    CategoryFaker categoryFaker = new CategoryFaker();
    ProductFaker productFaker = new ProductFaker();
    private final static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public CategoryLoader(
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            @Value("${category.count:100}") int count) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("[SEEDING] CategoryLoader");
        for (int i = 0; i < count && categoryRepository.count() < count; i++) {
            Category category = categoryFaker.getCategory();
            logger.info("[SEEDING] Category: {}, Count: {}", category, categoryRepository.count());
            categoryRepository.save(category);

            for (int j = i; j < count; j++) {
                Product product = productFaker.getProduct();
                product.setCategory(category);
                logger.info("[SEEDING] Product: {}, Count: {}", product, productRepository.count());
                productRepository.save(product);
            }
        }

    }
}
