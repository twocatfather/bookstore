package com.study.bookstore.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Review {
    private Long id;
    private String content;
    private int rating;
    private Long userId;
    private Long bookId;
    private LocalDateTime createdAt;

    public void validateRating() {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("rating는 1~5 사이의 정수입니다.");
        }
    }
}
