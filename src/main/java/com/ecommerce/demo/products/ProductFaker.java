package com.ecommerce.demo.products;

import com.github.javafaker.Faker;

public class ProductFaker {
    private final Faker faker;

    public ProductFaker() {
        this.faker = new Faker();
    }

    public String getName() {
        return faker.commerce().productName();
    }

    public String getDescription() {
        return faker.lorem().sentence();
    }

    public String getImage() {
        return faker.internet().image();
    }

    public Double getPrice() {
        return faker.number().randomDouble(2, 0, 1000);
    }

    public Integer getQuantity() {
        return faker.number().numberBetween(0, 100);
    }

    public Product getProduct() {
        Product product = new Product();
        product.setName(getName());
        product.setDescription(getDescription());
        product.setPrice(getPrice());
        product.setQuantity(getQuantity());
        return product;
    }

}
