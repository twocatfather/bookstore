package com.study.bookstore.infrastructure.category.entity;

import com.study.bookstore.domain.book.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class CategoryJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;

    public static CategoryJpaEntity from(Category category) {
        return CategoryJpaEntity.builder()
                .name(category.getName())
                .code(category.getCode())
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateCode(String code) {
        this.code = code;
    }
}
