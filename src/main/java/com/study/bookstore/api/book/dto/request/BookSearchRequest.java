package com.study.bookstore.api.book.dto.request;

public record BookSearchRequest(
        String title,
        String author,
        Integer minPrice,
        Integer maxPrice
) {
}
