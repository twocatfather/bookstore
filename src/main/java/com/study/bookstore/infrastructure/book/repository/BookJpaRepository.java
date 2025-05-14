package com.study.bookstore.infrastructure.book.repository;

import com.study.bookstore.infrastructure.book.entity.BookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookJpaRepository extends JpaRepository<BookJpaEntity, Long> {
    boolean existsByIsbnAndIsDeletedFalse(String isbn);

    @Query("""
            SELECT b FROM BookJpaEntity b 
            LEFT JOIN FETCH b.category c 
            WHERE b.isDeleted = false 
            AND (:categoryId IS NULL OR c.id = :categoryId)
    """)
    List<BookJpaEntity> findByCategoryWithFetch(@Param("categoryId") Long categoryId);
}
