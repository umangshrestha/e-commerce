package com.ecommerce.demo.utils.fakerService;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.demo.categories.Category;
import com.github.javafaker.Faker;

public class CategoryFakerService {
    private final Faker faker;
    private final Set<String> usednames;

    public CategoryFakerService() {
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
