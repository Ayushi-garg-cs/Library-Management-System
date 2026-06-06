package com.project.service;


import com.project.exception.BookException;
import com.project.payload.dto.BookDTO;
import com.project.payload.request.BookSearchRequest;
import com.project.payload.response.PageResponse;

import java.util.List;

public interface BookService {
    BookDTO createBook(BookDTO bookDTO) throws BookException;
    List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException;
    BookDTO getBookById(Long bookId) throws BookException;
    BookDTO getBookByISBN(String isbn) throws BookException;
    BookDTO updateBook(Long bookId,  BookDTO bookDTO) throws Exception;
    void deleteBook(Long bookId) throws BookException;
    void hardDeleteBook(Long bookId) throws BookException;
    PageResponse<BookDTO> searchBooksEithFilters(
        BookSearchRequest searchRequest
    );
    Long getTotalActiveBooks();
    Long getTotalAvailableBooks();
}
