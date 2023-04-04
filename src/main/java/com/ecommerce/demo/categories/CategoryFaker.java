package com.ecommerce.demo.categories;

import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;

public class CategoryFaker {
    private final Faker faker;
    private final Set<String> usednames;

    public CategoryFaker() {
        this.faker = new Faker();
        this.usednames = new HashSet<>();
    }

    public String getName() {
        String name;
        do {
            name = faker.commerce().department();
        } while (usednames.contains(name));
        usednames.add(name);
        return name;
    }

    public String getDescription() {
        return faker.lorem().sentence();
    }

    public Category getCategory() {
        Category category = new Category();
        category.setName(getName());
        category.setDescription(getDescription());
        return category;
    }
}
