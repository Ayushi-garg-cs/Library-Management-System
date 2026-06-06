package com.project.controller;


import com.project.exception.BookException;
import com.project.modal.Book;
import com.project.payload.dto.BookDTO;
import com.project.payload.request.BookSearchRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.PageResponse;
import com.project.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    //create a book
    @PostMapping
    public ResponseEntity<BookDTO> createBook( @Valid @RequestBody BookDTO bookDTO) throws BookException {
        BookDTO createdBook=bookService.createBook(bookDTO);
        return ResponseEntity.ok(createdBook);
    }

    @PostMapping("/bulk")
    public ResponseEntity<BookDTO> createBooksBulk( @Valid @RequestBody List<BookDTO> bookDTO) throws BookException {
        List<BookDTO> list=bookService.createBooksBulk(bookDTO);
        return  ResponseEntity.ok((BookDTO) list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) throws BookException {
        BookDTO bookDTO=bookService.getBookById(id);
        return ResponseEntity.ok(bookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) throws BookException {
        try{
            BookDTO updatedBook=bookService.updateBook(id,bookDTO);
            return ResponseEntity.ok(updatedBook);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) throws BookException {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new ApiResponse("Book deleted successfully",true));
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<ApiResponse> deleteBookPermanently(@PathVariable Long id) throws BookException {
        bookService.hardDeleteBook(id);
        return ResponseEntity.ok(new ApiResponse("Book permanently deleted",true));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookDTO>> searchBooks(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false, defaultValue = "false") Boolean availableOnly,
            @RequestParam( defaultValue = "true") Boolean activeOnly,
            @RequestParam( defaultValue = "0") int page,
            @RequestParam( defaultValue = "20") int size,
            @RequestParam( defaultValue = "createdAt") String sortBy,
            @RequestParam( defaultValue = "DESC") String sortDirection
    ){
        //build search requests from query parameters
        BookSearchRequest bookSearchRequest=new BookSearchRequest();
        bookSearchRequest.setGenreId(genreId);
        bookSearchRequest.setAvailableOnly(availableOnly);
        bookSearchRequest.setPage(page);
        bookSearchRequest.setSize(size);
        bookSearchRequest.setSortBy(sortBy);
        bookSearchRequest.setSortDirection(sortDirection);

        PageResponse<BookDTO> books=bookService.searchBooksEithFilters(bookSearchRequest);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<BookDTO>> advancedSearch(@RequestBody BookSearchRequest bookSearchRequest) throws BookException {
        PageResponse<BookDTO> books=bookService.searchBooksEithFilters(bookSearchRequest);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/stats")
    public ResponseEntity<BookStatsResponse> getBookStats(){
        long totalActive=bookService.getTotalActiveBooks();
        long totalAvailable=bookService.getTotalAvailableBooks();

        BookStatsResponse stats=new BookStatsResponse(totalActive,totalAvailable);
        return ResponseEntity.ok(stats);
    }

    //statistics response dto
    public static class BookStatsResponse{
        public long totalActiveBooks;
        public long totalAvailableBooks;

        public BookStatsResponse(long totalActiveBooks,long totalAvailableBooks){
            this.totalActiveBooks=totalActiveBooks;
            this.totalAvailableBooks=totalAvailableBooks;
        }
    }

}
