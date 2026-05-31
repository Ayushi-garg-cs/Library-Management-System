package com.project.service.impl;

import com.project.exception.BookException;
import com.project.payload.dto.BookDTO;
import com.project.payload.request.BookSearchRequest;
import com.project.payload.response.PageResponse;
import com.project.repository.BookRepository;
import com.project.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    @Override
    public BookDTO createBook(BookDTO bookDTO) throws BookException {
        if(bookRepository.existsByISBN(bookDTO.getIsbn())){
            throw new BookException("book with ISBN: "+bookDTO.getIsbn()+" already exists");
        }
        return null;
    }

    @Override
    public List<BookDTO> createBooksBulk() {
        return List.of();
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        return null;
    }

    @Override
    public BookDTO getBookByISBN(String isbn) {
        return null;
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        return null;
    }

    @Override
    public void deleteBook(Long bookId) {

    }

    @Override
    public void hardDeleteBook(Long bookId) {

    }

    @Override
    public PageResponse<BookDTO> searchBooksEithFilters(BookSearchRequest searchRequest) {
        return null;
    }

    @Override
    public Long getTotalActiveBooks() {
        return 0L;
    }

    @Override
    public Long getTotalAvailableBooks() {
        return 0L;
    }
}
