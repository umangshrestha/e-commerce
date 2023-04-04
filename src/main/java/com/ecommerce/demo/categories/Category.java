package com.ecommerce.demo.categories;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.demo.products.Product;
import com.ecommerce.demo.products.ProductView;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @JsonView({ CategoryView.Public.class, ProductView.Public.class })
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @JsonView({ CategoryView.Public.class, ProductView.Public.class })
    @Column(unique = true)
    private String name;

    @Size(max = 255)
    @JsonView(CategoryView.Public.class)
    private String description;

    @Column(name = "created_at", updatable = false)
    @JsonView(CategoryView.Public.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonView(CategoryView.Public.class)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @ToString.Exclude
    @JsonView(CategoryView.WithProducts.class)
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

}
