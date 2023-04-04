package com.ecommerce.demo.products;

import java.time.LocalDateTime;

import com.ecommerce.demo.categories.Category;
import com.ecommerce.demo.categories.CategoryView;
import com.ecommerce.demo.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ ProductView.Public.class, CategoryView.Public.class })
    private Long id;

    @NotBlank
    @JsonView({ ProductView.Public.class, CategoryView.Public.class })
    private String name;

    @Size(max = 1000)
    @JsonView({ ProductView.Public.class, CategoryView.Public.class })
    private String description;

    @NotNull
    @Positive
    @JsonView({ ProductView.Public.class, CategoryView.Public.class })
    private Double price = 1.0;

    @JsonView({ ProductView.Public.class, CategoryView.Public.class })
    private Integer quantity;

    @Column(name = "created_at", updatable = false)
    @JsonView({ ProductView.Public.class, CategoryView.Public.class })
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonView({ ProductView.Public.class, CategoryView.Public.class })
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @JsonView(ProductView.WithDeleteDate.class)
    private LocalDateTime deletedAt;

    @ToString.Exclude
    @JsonView(ProductView.Public.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
