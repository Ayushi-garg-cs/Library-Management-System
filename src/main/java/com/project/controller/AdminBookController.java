package com.project.controller;

import com.project.exception.BookException;
import com.project.payload.dto.BookDTO;
import com.project.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/book")
public class AdminBookController {


    private final BookService bookService;

    //create a book
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) throws BookException {
        BookDTO createdBook=bookService.createBook(bookDTO);
        return ResponseEntity.ok(createdBook);
    }
}
