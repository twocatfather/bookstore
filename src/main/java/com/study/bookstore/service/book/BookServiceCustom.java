package com.study.bookstore.service.book;

import com.study.bookstore.domain.book.Book;
import com.study.bookstore.infrastructure.book.entity.BookJpaEntity;
import com.study.bookstore.infrastructure.book.repository.BookJpaRepository;
import com.study.bookstore.infrastructure.category.entity.CategoryJpaEntity;
import com.study.bookstore.infrastructure.category.repository.CategoryJpaRepository;
import com.study.bookstore.service.book.dto.BookServiceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceCustom implements BookService{
    private final BookJpaRepository bookJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    @Transactional
    public Book createBook(BookServiceRequest request) {
        validateIsbnUnique(request.isbn());
        Book book = Book.create(
                request.title(),
                request.author(),
                request.isbn(),
                request.price(),
                request.stockQuantity(),
                null
        );
        BookJpaEntity save = bookJpaRepository.save(BookJpaEntity.from(book));

        return Book.from(save);
    }

    // updateStock , BookJpaEntity update 메소드
    @Override
    public Book updateBook(Long bookId, int quantity) {
        return null;
    }

    // findActiveById -> 메소드화 findActiveBookEntityById(Long id);
    @Override
    public Book getBookById(Long bookId) {
        return null;
    }

    // findActiveBookEntityById 객체 찾고 해당 객체 delete()
    @Override
    public void deleteBook(Long id) {

    }

    @Override
    public Book createBookWithCategory(BookServiceRequest request, Long categoryId) {
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        BookJpaEntity bookJpaEntity = BookJpaEntity.builder()
                .title(request.title())
                .author(request.author())
                .isbn(request.isbn())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .category(categoryJpaEntity)
                .build();

        BookJpaEntity savedEntity = bookJpaRepository.save(bookJpaEntity);
        return Book.from(savedEntity);
    }

    @Override
    public List<Book> getBooksByCategory(Long categoryId) {
        return bookJpaRepository.findByCategoryWithFetch(categoryId).stream()
                .map(Book::from)
                .toList();
    }

    private void validateIsbnUnique(String isbn) {
        if (bookJpaRepository.existsByIsbnAndIsDeletedFalse(isbn)) {
            throw new IllegalArgumentException("이미 존재하는 ISBN입니다.");
        }
    }
}
