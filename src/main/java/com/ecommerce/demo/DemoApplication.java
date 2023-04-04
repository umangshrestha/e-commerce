package com.ecommerce.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ecommerce.demo.categories.Category;
import com.ecommerce.demo.categories.CategoryRepository;
import com.ecommerce.demo.products.Product;
import com.ecommerce.demo.products.ProductRepository;
import com.ecommerce.demo.utils.fakerService.CategoryFakerService;
import com.ecommerce.demo.utils.fakerService.ProductFakerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class DemoApplication {
	@Value("${seeding.count:10}")
	int count;

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductRepository productRepository;

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRinner() {
		return args -> {
			CategoryFakerService categoryFaker = new CategoryFakerService();
			ProductFakerService productFaker = new ProductFakerService();
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
		};
	}
}
