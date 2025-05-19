package com.study.bookstore.infrastructure.book.repository;

import com.study.bookstore.infrastructure.book.entity.BookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    @Query("SELECT b FROM BookJpaEntity b WHERE b.isDeleted = false AND b.id = :id")
    Optional<BookJpaEntity> findActiveById(@Param("id") Long id);

    @Query("""
            SELECT b FROM BookJpaEntity b WHERE b.isDeleted = false AND 
            (:title IS NULL OR b.title LIKE %:title%) AND 
            (:author IS NULL OR b.author LIKE %:author%) AND 
            (:minPrice IS NULL OR b.price >= :minPrice) AND
            (:maxPrice IS NULL OR b.price <= :maxPrice)
            """)
    List<BookJpaEntity> searchBooks(
            @Param("title") String title,
            @Param("author") String author,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice
    );

    @Query("SELECT b FROM BookJpaEntity b WHERE b.isDeleted = false")
    List<BookJpaEntity> findAllActive();

    @Query("SELECT b FROM BookJpaEntity b WHERE b.isDeleted = false AND b.author = :author")
    List<BookJpaEntity> findByAuthor(@Param("author") String author);
}
