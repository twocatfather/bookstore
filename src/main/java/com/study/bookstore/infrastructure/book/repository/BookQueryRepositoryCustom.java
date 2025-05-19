package com.study.bookstore.infrastructure.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.bookstore.domain.book.Book;
import com.study.bookstore.infrastructure.book.entity.QBookJpaEntity;
import com.study.bookstore.service.book.dto.BookSearchCriteria;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookQueryRepositoryCustom implements BookQueryRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    private BooleanExpression isDeletedFalse() {
        return QBookJpaEntity.bookJpaEntity.isDeleted.isFalse();
    }

    private BooleanExpression titleContains(String title) {
        return title != null ?
                QBookJpaEntity.bookJpaEntity.title.contains(title) : null;
    }

    private BooleanExpression authorEquals(String author) {
        return author != null ?
                QBookJpaEntity.bookJpaEntity.author.eq(author) : null;
    }

    private BooleanExpression priceGoe(Integer minPrice) {
        return minPrice != null ?
                QBookJpaEntity.bookJpaEntity.price.goe(minPrice) : null;
    }

    private BooleanExpression priceLoe(Integer maxPrice) {
        return maxPrice != null ?
                QBookJpaEntity.bookJpaEntity.price.loe(maxPrice) : null;
    }

    @Override
    public List<Book> searchBooks(BookSearchCriteria criteria) {

        QBookJpaEntity bookJpaEntity = QBookJpaEntity.bookJpaEntity;
        return queryFactory
                .selectFrom(bookJpaEntity)
                .where(
                        isDeletedFalse(),
                        titleContains(criteria.title()),
                        authorEquals(criteria.author()),
                        priceGoe(criteria.minPrice()),
                        priceLoe(criteria.maxPrice())
                )
                .fetch()
                .stream()
                .map(Book::from)
                .toList();
    }
}
