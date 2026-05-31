package com.project.service;


import com.project.exception.BookException;
import com.project.payload.dto.BookDTO;
import com.project.payload.request.BookSearchRequest;
import com.project.payload.response.PageResponse;

import java.util.List;

public interface BookService {
    BookDTO createBook(BookDTO bookDTO) throws BookException;
    List<BookDTO> createBooksBulk();
    BookDTO getBookById(Long bookId);
    BookDTO getBookByISBN(String isbn);
    BookDTO updateBook(Long bookId,  BookDTO bookDTO);
    void deleteBook(Long bookId);
    void hardDeleteBook(Long bookId);
    PageResponse<BookDTO> searchBooksEithFilters(
        BookSearchRequest searchRequest
    );
    Long getTotalActiveBooks();
    Long getTotalAvailableBooks();
}
