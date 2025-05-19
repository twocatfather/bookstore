package com.study.bookstore.api.book.controller;

import com.study.bookstore.api.book.dto.request.BookRequest;
import com.study.bookstore.api.book.dto.request.BookSearchRequest;
import com.study.bookstore.api.book.dto.request.BookUpdateRequest;
import com.study.bookstore.api.book.dto.request.BookWithCategoryRequest;
import com.study.bookstore.api.book.dto.response.BookResponse;
import com.study.bookstore.service.book.facade.BookFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping("/{id}/stock")
    @Operation(summary = "Update stock", description = "Update stock quantity of a book")
    public BookResponse updateStock(
            @PathVariable @Positive(message = "Id must be positive") Long id,
            @RequestParam @NotNull(message = "Quantity is required") Integer quantity
    ) {
        return bookFacadeService.updateStock(id, quantity);
    }

    // getBookById
    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Get book by id")
    public BookResponse getBookById(@PathVariable @Positive(message = "Id must be positive") Long id) {
        return bookFacadeService.getBookById(id);
    }

    // /category/{id}
    // getBooksByCategory List<BookResponse>
    @GetMapping("/category/{id}")
    @Operation(summary = "Get books by category")
    public ResponseEntity<List<BookResponse>> getBooksByCategory(
            @PathVariable @Positive(message = "categoryId must be positive") Long id) {
        return ResponseEntity.ok(bookFacadeService.getBooksByCategory(id));
    }

    // getAllBooks List<BookResponse>
    @GetMapping
    @Operation(summary = "Get all books")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookFacadeService.getAllBooks());
    }

    // /author/{author}
    // List<BookResponse>
    @GetMapping("/author/{author}")
    @Operation(summary = "Get Books by author")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(
            @PathVariable String author
    ) {
        return ResponseEntity.ok(bookFacadeService.getBooksByAuthor(author));
    }

    // updateBookDetails BookUpdateRequest dto stock, price
    @PatchMapping("/{id}")
    @Operation(summary = "update book details")
    public ResponseEntity<BookResponse> updateBookDetails(
            @PathVariable @Positive(message = "Id must be positive") Long id,
            @Valid @RequestBody BookUpdateRequest request
    ) {
        return ResponseEntity.ok(bookFacadeService.updateBookDetails(id, request));
    }

    // deleteBook
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by id", description = "Delete book by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable @Positive(message = "Id must be positive") Long id) {
        bookFacadeService.deleteById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search books by criteria")
    public ResponseEntity<List<BookResponse>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        BookSearchRequest request = new BookSearchRequest(title, author, minPrice, maxPrice);
        return ResponseEntity.ok(bookFacadeService.searchBooks(request));
    }

    @GetMapping("/search2")
    @Operation(summary = "Search books by criteria")
    public ResponseEntity<List<BookResponse>> searchBooks2(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        BookSearchRequest request = new BookSearchRequest(title, author, minPrice, maxPrice);
        return ResponseEntity.ok(bookFacadeService.searchBooks2(request));
    }
}
