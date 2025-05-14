package com.study.bookstore.api.book.dto.response;

import com.study.bookstore.domain.book.Book;
import lombok.Builder;

@Builder
public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        Integer price,
        Integer stockQuantity,
        String category
) {
    public static BookResponse from(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .category(book.getCategory() != null ? book.getCategory().getName() : null)
                .build();
    }
}
