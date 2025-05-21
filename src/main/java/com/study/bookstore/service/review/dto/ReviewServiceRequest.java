package com.study.bookstore.service.review.dto;

public record ReviewServiceRequest(
        String content,
        int rating,
        Long bookId
) {
}
