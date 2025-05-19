package com.study.bookstore.infrastructure.book.repository;

import com.study.bookstore.domain.book.Book;
import com.study.bookstore.service.book.dto.BookSearchCriteria;

import java.util.List;

public interface BookQueryRepository {
    List<Book> searchBooks(BookSearchCriteria criteria);
}
