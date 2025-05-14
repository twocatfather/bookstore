package com.study.bookstore.api.book.controller;

import com.study.bookstore.api.book.dto.request.BookRequest;
import com.study.bookstore.api.book.dto.request.BookWithCategoryRequest;
import com.study.bookstore.api.book.dto.response.BookResponse;
import com.study.bookstore.service.book.facade.BookFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "BOOK", description = "BOOK API")
@Validated
public class BookController {
    private final BookFacadeService bookFacadeService;

    @PostMapping
    @Operation(summary = "Save book", description = "Create a new book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@Valid @RequestBody BookRequest request) {
        return bookFacadeService.createBook(request);
    }

    @PostMapping("/category")
    @Operation(summary = "Save book", description = "Create a new book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBookWithCategory(@Valid @RequestBody BookWithCategoryRequest request) {
        return bookFacadeService.createBookWithCategory(request);
    }

    // update
    // getBookById
    // deleteBook
}
