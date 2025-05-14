package com.study.bookstore.domain.book;

import com.study.bookstore.infrastructure.book.entity.BookJpaEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Book {
    private final Long id;
    private final String title;
    private final String author;
    private final String isbn;
    private int price;
    private int stockQuantity;
    private Category category;
    private boolean isDeleted;

    public Book(Long id, String title, String author,
                String isbn, int price, int stockQuantity,
                Category category, boolean isDeleted) {
        validateFields(title, author, isbn, price, stockQuantity);
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.isDeleted = isDeleted;
    }

    private static void validateFields(String title, String author,
                                       String isbn, int price, int stockQuantity) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("제목은 필수입니다.");
        if (author == null || author.isBlank()) throw new IllegalArgumentException("저자는 필수입니다.");
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("ISBN은 필수입니다.");
        if (price < 0) throw new IllegalArgumentException("가격은 0보다 커야합니다.");
        if (stockQuantity < 0) throw new IllegalArgumentException("재고는 0보다 커야합니다.");
    }

    public void updateStock(int quantity) {
        int newQuantity = this.stockQuantity + quantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다. 현재 재고: " + this.stockQuantity);
        }
        this.stockQuantity = newQuantity;
    }

    public void updatePrice(int newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("가격은 0보다 커야합니다.");
        }
        this.price = newPrice;
    }

    public static Book create(String title, String author, String isbn
                              , int price, int stockQuantity, Category category) {
        return Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category)
                .build();
    }

    public static Book from(BookJpaEntity entity) {
        return Book.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .isbn(entity.getIsbn())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .category(entity.getCategory() != null ?
                        Category.create(entity.getCategory().getName(),
                                entity.getCategory().getCode()) : null)
                .build();
    }
}
