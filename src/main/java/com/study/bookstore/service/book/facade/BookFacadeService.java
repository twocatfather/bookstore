package com.study.bookstore.service.book.facade;

import com.study.bookstore.api.book.dto.request.BookRequest;
import com.study.bookstore.api.book.dto.request.BookSearchRequest;
import com.study.bookstore.api.book.dto.request.BookUpdateRequest;
import com.study.bookstore.api.book.dto.request.BookWithCategoryRequest;
import com.study.bookstore.api.book.dto.response.BookResponse;
import com.study.bookstore.domain.book.Book;
import com.study.bookstore.service.book.BookService;
import com.study.bookstore.service.book.dto.BookSearchCriteria;
import com.study.bookstore.service.book.dto.BookServiceRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public BookResponse updateStock(Long bookId, int quantity) {
        Book book = bookService.updateBook(bookId, quantity);
        return BookResponse.from(book);
    }

    // getBookById
    public BookResponse getBookById(Long bookId) {
        return BookResponse.from(bookService.getBookById(bookId));
    }

    // deleteById
    @Transactional
    public void deleteById(Long id) {
        bookService.deleteBook(id);
    }

    public List<BookResponse> searchBooks(BookSearchRequest request) {
        BookSearchCriteria criteria = getSearchCriteria(request);

        return bookService.searchBooks(criteria).stream()
                .map(BookResponse::from)
                .toList();
    }

    public List<BookResponse> searchBooks2(BookSearchRequest request) {
        BookSearchCriteria criteria = getSearchCriteria(request);

        return bookService.searchBooks2(criteria).stream()
                .map(BookResponse::from)
                .toList();
    }

    private BookSearchCriteria getSearchCriteria(BookSearchRequest request) {
        return BookSearchCriteria.builder()
                .title(request.title())
                .author(request.author())
                .minPrice(request.minPrice())
                .maxPrice(request.maxPrice())
                .build();
    }

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

    public List<BookResponse> getBooksByCategory(Long id) {
        return bookService.getBooksByCategory(id)
                .stream()
                .map(BookResponse::from)
                .toList();
    }

    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(BookResponse::from)
                .toList();
    }

    public List<BookResponse> getBooksByAuthor(String author) {
        return bookService.getBooksByAuthor(author).stream()
                .map(BookResponse::from)
                .toList();
    }

    @Transactional
    public BookResponse updateBookDetails(Long id, BookUpdateRequest request) {
        return BookResponse.from(bookService.updateBookDetails(id, request));
    }
}
