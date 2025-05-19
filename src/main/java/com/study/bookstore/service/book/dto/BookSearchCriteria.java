package com.study.bookstore.service.book.dto;

import lombok.Builder;

@Builder
public record BookSearchCriteria(
        String title,
        String author,
        Integer minPrice,
        Integer maxPrice
) {
}
