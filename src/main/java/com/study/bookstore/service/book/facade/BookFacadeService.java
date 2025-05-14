package com.study.bookstore.service.book.facade;

import com.study.bookstore.api.book.dto.request.BookRequest;
import com.study.bookstore.api.book.dto.request.BookWithCategoryRequest;
import com.study.bookstore.api.book.dto.response.BookResponse;
import com.study.bookstore.domain.book.Book;
import com.study.bookstore.service.book.BookService;
import com.study.bookstore.service.book.dto.BookServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookFacadeService {
    private final BookService bookService;

    @Transactional
    public BookResponse createBook(BookRequest request) {
        validateBookRequest(request);
        Book book = bookService.createBook(toServiceRequest(request));
        return BookResponse.from(book);
    }

    @Transactional
    public BookResponse createBookWithCategory(BookWithCategoryRequest request) {
        validateBookRequest(request);
        Book bookWithCategory =
                bookService.createBookWithCategory(toServiceRequest(request), request.categoryId());
        return BookResponse.from(bookWithCategory);
    }

    // updateStock

    // getBookById

    // deleteById

    private BookServiceRequest toServiceRequest(BookRequest request) {
        return new BookServiceRequest(
                request.title(),
                request.author(),
                request.isbn(),
                request.price(),
                request.stockQuantity(),
                null
        );
    }

    private BookServiceRequest toServiceRequest(BookWithCategoryRequest request) {
        return new BookServiceRequest(
                request.title(),
                request.author(),
                request.isbn(),
                request.price(),
                request.stockQuantity(),
                request.categoryId()
        );
    }

    private void validateBookRequest(BookRequest request) {
        if (request.price() < 0) {
            throw new IllegalArgumentException("가격은 0보다 커야합니다.");
        }
        if (request.stockQuantity() < 0) {
            throw new IllegalArgumentException("재고는 0보다 커야합니다.");
        }
    }

    private void validateBookRequest(BookWithCategoryRequest request) {
        if (request.price() < 0) {
            throw new IllegalArgumentException("가격은 0보다 커야합니다.");
        }
        if (request.stockQuantity() < 0) {
            throw new IllegalArgumentException("재고는 0보다 커야합니다.");
        }
    }
}
