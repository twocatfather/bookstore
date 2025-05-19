package com.study.bookstore.api.book.dto.request;

public record BookUpdateRequest(
        Integer price,
        Integer stockQuantity
) {
}
