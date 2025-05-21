package com.study.bookstore.infrastructure.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.bookstore.infrastructure.review.entity.QReviewJpaEntity;
import com.study.bookstore.infrastructure.user.entity.QUserJpaEntity;
import com.study.bookstore.service.review.dto.ReviewServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryCustom implements ReviewQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewServiceResponse> findReviewsByBookId(Long bookId, Pageable pageable) {
        QReviewJpaEntity review = QReviewJpaEntity.reviewJpaEntity;
        QUserJpaEntity user = QUserJpaEntity.userJpaEntity;

        List<ReviewServiceResponse> content = queryFactory
                .select(Projections.constructor(ReviewServiceResponse.class,
                        review.id,
                        review.content,
                        review.rating,
                        user.username,
                        review.createdAt
                ))
                .from(review)
                .leftJoin(review.user, user)
                .where(
                        review.book.id.eq(bookId),
                        review.isDeleted.isFalse()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(review.createdAt.desc())
                .fetch();

        Long total = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        review.book.id.eq(bookId),
                        review.isDeleted.isFalse()
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
