package com.study.bookstore.service.review;

import com.study.bookstore.domain.review.Review;
import com.study.bookstore.infrastructure.book.entity.BookJpaEntity;
import com.study.bookstore.infrastructure.book.repository.BookJpaRepository;
import com.study.bookstore.infrastructure.review.entity.ReviewJpaEntity;
import com.study.bookstore.infrastructure.review.repository.ReviewJpaRepository;
import com.study.bookstore.infrastructure.user.entity.UserJpaEntity;
import com.study.bookstore.infrastructure.user.repository.UserJpaRepository;
import com.study.bookstore.service.review.dto.ReviewServiceRequest;
import com.study.bookstore.service.review.dto.ReviewServiceResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewJpaRepository reviewJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final BookJpaRepository bookJpaRepository;

    @Transactional
    public ReviewServiceResponse createReview(ReviewServiceRequest request, Long userId) {
        UserJpaEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        BookJpaEntity book = bookJpaRepository.findById(request.bookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Review review = Review.builder()
                .content(request.content())
                .rating(request.rating())
                .userId(user.getId())
                .bookId(book.getId())
                .createdAt(LocalDateTime.now())
                .build();

        review.validateRating();

        ReviewJpaEntity reviewJpaEntity = reviewJpaRepository.save(ReviewJpaEntity.from(review, user, book));
        return convertToResponse(reviewJpaEntity, user.getUsername());
    }

    @Transactional
    public ReviewServiceResponse updateReview(Long reviewId, ReviewServiceRequest request, Long userId) {
        ReviewJpaEntity entity = reviewJpaRepository.findByIdAndUser(reviewId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        entity.updateContent(request.content());
        entity.updateRating(request.rating());
        return convertToResponse(entity, entity.getUser().getUsername());
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        ReviewJpaEntity entity = reviewJpaRepository.findByIdAndUser(reviewId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        entity.delete();
    }

    @Transactional(readOnly = true)
    public Page<ReviewServiceResponse> getReviewsByUser(Long userId, Pageable pageable) {
        log.info("getReviewsByUser userId: {}", userId);
        Page<ReviewJpaEntity> entities = reviewJpaRepository.findByUserIdAndIsDeletedFalse(userId, pageable);
        return entities.map(entity -> convertToResponse(entity, entity.getUser().getUsername()));
    }

    @Transactional(readOnly = true)
    public Page<ReviewServiceResponse> getReviewsByBook(Long bookId, Pageable pageable) {
        Page<ReviewJpaEntity> entities = reviewJpaRepository.findByBookIdAndIsDeletedFalse(bookId, pageable);
        return entities.map(entity -> convertToResponse(entity, entity.getUser().getUsername()));
    }

    @Transactional(readOnly = true)
    public ReviewServiceResponse getReview(Long reviewId) {
        ReviewJpaEntity reviewEntity = reviewJpaRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        if (reviewEntity.isDeleted()) {
            throw new IllegalArgumentException("Review not found");
        }

        return convertToResponse(reviewEntity, reviewEntity.getUser().getUsername());
    }

    public Page<ReviewServiceResponse> getPagedReviews(Long bookId, Pageable pageable) {
        return reviewJpaRepository.findReviewsByBookId(bookId, pageable);
    }

    public double calculateAverageRating(Long bookId) {
        return reviewJpaRepository.findByBookIdAndIsDeletedFalse(bookId).stream()
                .mapToInt(ReviewJpaEntity::getRating)
                .average()
                .orElse(0.0);
    }

    private ReviewServiceResponse convertToResponse(ReviewJpaEntity reviewJpaEntity, String username) {
        return new ReviewServiceResponse(
                reviewJpaEntity.getId(),
                reviewJpaEntity.getContent(),
                reviewJpaEntity.getRating(),
                username,
                reviewJpaEntity.getCreatedAt()
        );
    }
}