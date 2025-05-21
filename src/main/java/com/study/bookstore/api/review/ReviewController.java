package com.study.bookstore.api.review;

import com.study.bookstore.global.jwt.CustomUserDetails;
import com.study.bookstore.service.review.ReviewService;
import com.study.bookstore.service.review.dto.ReviewServiceRequest;
import com.study.bookstore.service.review.dto.ReviewServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Save review", description = "Create a new review")
    public ResponseEntity<ReviewServiceResponse> createReview(
            @Valid @RequestBody ReviewServiceRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long id = userDetails.getUser().getId();
        ReviewServiceResponse review = reviewService.createReview(request, id);
        return ResponseEntity.status(201).body(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewServiceResponse> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewServiceRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long id = userDetails.getUser().getId();
        ReviewServiceResponse reviewServiceResponse = reviewService.updateReview(reviewId, request, id);
        return ResponseEntity.ok(reviewServiceResponse);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        reviewService.deleteReview(reviewId, userDetails.getUser().getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Page<ReviewServiceResponse>> getReviewsByBook(
            @PathVariable Long bookId,
            @PageableDefault(sort = "rating", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ReviewServiceResponse> reviewsByBook = reviewService.getReviewsByBook(bookId, pageable);
        return ResponseEntity.ok(reviewsByBook);
    }

    @GetMapping("/my-reviews")
    public ResponseEntity<Page<ReviewServiceResponse>> getMyReviews(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Page<ReviewServiceResponse> reviewsByUser = reviewService.getReviewsByUser(userDetails.getUserId(), pageable);
        return ResponseEntity.ok(reviewsByUser);
    }

    // 도서별 평균 평점 조회 (인증이 불필요)
    // getAverageRating -> /books/{bookId}/average-rating
    // Double -> 0.0 ~ 5.0
    // reviewService -> calculateAverageRating(bookId)

    @GetMapping("/books/{bookId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {
        double averageRating = reviewService.calculateAverageRating(bookId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewServiceResponse> getReview(@PathVariable Long reviewId) {
        ReviewServiceResponse review = reviewService.getReview(reviewId);
        return ResponseEntity.ok(review);
    }
}
