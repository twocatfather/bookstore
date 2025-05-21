package com.study.bookstore.infrastructure.review.repository;

import com.study.bookstore.infrastructure.review.entity.ReviewJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewJpaRepository extends JpaRepository<ReviewJpaEntity, Long>, ReviewQueryRepository {

    @EntityGraph(attributePaths = {"user", "book"})
    Page<ReviewJpaEntity> findByBook_IdAndIsDeletedFalse(Long bookId, Pageable pageable);

    List<ReviewJpaEntity> findByBookIdAndIsDeletedFalse(Long bookId);

    @Query("SELECT r FROM ReviewJpaEntity r WHERE r.id = :reviewId AND r.user.id = :userId")
    Optional<ReviewJpaEntity> findByIdAndUser(
            @Param("reviewId") Long reviewId,
            @Param("userId") Long userId
    );

    @EntityGraph(attributePaths = {"user", "book"})
    Page<ReviewJpaEntity> findByUser_IdAndIsDeletedFalse(Long userId, Pageable pageable);
}
