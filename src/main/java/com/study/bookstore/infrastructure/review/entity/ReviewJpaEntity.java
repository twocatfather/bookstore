package com.study.bookstore.infrastructure.review.entity;

import com.study.bookstore.domain.review.Review;
import com.study.bookstore.global.common.BaseEntity;
import com.study.bookstore.infrastructure.book.entity.BookJpaEntity;
import com.study.bookstore.infrastructure.user.entity.UserJpaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "review")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewJpaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Min(1)@Max(5)
    @Column(name = "rating")
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookJpaEntity book;

    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    // ReviewJpaEntity from
    public static ReviewJpaEntity from(Review review, UserJpaEntity user, BookJpaEntity book) {
        return ReviewJpaEntity.builder()
                .content(review.getContent())
                .rating(review.getRating())
                .user(user)
                .book(book)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateRating(int rating) {
        this.rating = rating;
    }
}
