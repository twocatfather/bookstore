package com.study.bookstore.service.review.dto;

import java.time.LocalDateTime;

public record ReviewServiceResponse(
        Long reviewId,
        String content,
        int rating,
        String username,
        LocalDateTime createdAt
) {
}
