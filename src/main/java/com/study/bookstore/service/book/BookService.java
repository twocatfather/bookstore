package com.study.bookstore.service.book;

import com.study.bookstore.api.book.dto.request.BookUpdateRequest;
import com.study.bookstore.domain.book.Book;
import com.study.bookstore.service.book.dto.BookSearchCriteria;
import com.study.bookstore.service.book.dto.BookServiceRequest;

import java.util.Collection;
import java.util.List;

public interface BookService {
    Book createBook(BookServiceRequest request);
    Book updateBook(Long bookId, int quantity);
    Book getBookById(Long bookId);
    void deleteBook(Long id);
    Book createBookWithCategory(BookServiceRequest request, Long categoryId);
    List<Book> getBooksByCategory(Long categoryId);
    List<Book> searchBooks(BookSearchCriteria criteria);
    List<Book> searchBooks2(BookSearchCriteria criteria);

    List<Book> getAllBooks();

    List<Book> getBooksByAuthor(String author);

    Book updateBookDetails(Long id, BookUpdateRequest request);
}
