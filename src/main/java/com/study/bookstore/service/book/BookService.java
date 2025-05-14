package com.study.bookstore.service.book;

import com.study.bookstore.domain.book.Book;
import com.study.bookstore.service.book.dto.BookServiceRequest;

import java.util.List;

public interface BookService {
    Book createBook(BookServiceRequest request);
    Book updateBook(Long bookId, int quantity);
    Book getBookById(Long bookId);
    void deleteBook(Long id);
    Book createBookWithCategory(BookServiceRequest request, Long categoryId);
    List<Book> getBooksByCategory(Long categoryId);
}
