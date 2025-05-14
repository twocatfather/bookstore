package com.study.bookstore.infrastructure.book.entity;

import com.study.bookstore.domain.book.Book;
import com.study.bookstore.global.common.BaseEntity;
import com.study.bookstore.infrastructure.category.entity.CategoryJpaEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookJpaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    public static BookJpaEntity from(Book book) {
        return BookJpaEntity.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .category(book.getCategory() != null ?
                        CategoryJpaEntity.from(book.getCategory())
                        : null)
                .build();
    }

    public void update(Book book) {
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        if (book.getCategory() != null) {
            this.category = CategoryJpaEntity.from(book.getCategory());
        }
    }
}
