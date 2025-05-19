package com.study.bookstore.infrastructure.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.bookstore.service.review.dto.ReviewServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryCustom implements ReviewQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewServiceResponse> findReviewsByBookId(Long bookId, Pageable pageable) {
        // 정렬 순서 최신순 createdAt
        return null;
    }
}
