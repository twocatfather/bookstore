package com.study.bookstore.service.book.dto;

public record BookServiceRequest(
        String title,
        String author,
        String isbn,
        Integer price,
        Integer stockQuantity,
        Long categoryId
) {
}
