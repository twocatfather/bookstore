package com.study.bookstore.infrastructure.review.repository;

import com.study.bookstore.infrastructure.review.entity.ReviewJpaEntity;
import com.study.bookstore.service.review.dto.ReviewServiceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewJpaRepository extends JpaRepository<ReviewJpaEntity, Long> {

    @Query("SELECT r FROM ReviewJpaEntity r JOIN FETCH r.user JOIN FETCH r.book WHERE r.user.id = :userId AND r.isDeleted = false")
    List<ReviewJpaEntity> findAllByUserIdAndIsDeletedFalse(@Param("userId") Long userId);
    
    @Query(value = "SELECT r FROM ReviewJpaEntity r JOIN r.user u WHERE u.id = :userId AND r.isDeleted = false",
           countQuery = "SELECT COUNT(r) FROM ReviewJpaEntity r JOIN r.user u WHERE u.id = :userId AND r.isDeleted = false")
    Page<ReviewJpaEntity> findByUserIdAndIsDeletedFalse(@Param("userId") Long userId, Pageable pageable);
    
    List<ReviewJpaEntity> findByBookIdAndIsDeletedFalse(Long bookId);
    
    Page<ReviewJpaEntity> findByBookIdAndIsDeletedFalse(Long bookId, Pageable pageable);
    
    @Query("SELECT r FROM ReviewJpaEntity r WHERE r.id = :reviewId AND r.user.id = :userId AND r.isDeleted = false")
    Optional<ReviewJpaEntity> findByIdAndUser(@Param("reviewId") Long reviewId, @Param("userId") Long userId);
    
    @Query("SELECT new com.study.bookstore.service.review.dto.ReviewServiceResponse(" +
           "r.id, r.content, r.rating, r.user.username, r.createdAt) " +
           "FROM ReviewJpaEntity r " +
           "WHERE r.book.id = :bookId AND r.isDeleted = false")
    Page<ReviewServiceResponse> findReviewsByBookId(@Param("bookId") Long bookId, Pageable pageable);
}
