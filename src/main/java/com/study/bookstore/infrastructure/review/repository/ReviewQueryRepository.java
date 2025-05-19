package com.study.bookstore.infrastructure.review.repository;

import com.study.bookstore.service.review.dto.ReviewServiceResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryRepository {
    Page<ReviewServiceResponse> findReviewsByBookId(Long bookId, Pageable pageable);
}
