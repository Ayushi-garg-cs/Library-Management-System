package com.project.mapper;


import com.project.exception.BookException;
import com.project.modal.Book;
import com.project.modal.Genre;
import com.project.payload.dto.BookDTO;
import com.project.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final GenreRepository genreRepository;
    public BookDTO toDTO(Book book){
        if(book==null){
            return null;
        }
        BookDTO dto =BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .availableCopies(book.getAvailableCopies())
                .totalCopies(book.getTotalCopies())
                .genreId(book.getGenre().getId())
                .genreName(book.getGenre().getName())
                .genreCode(book.getGenre().getCode())
                .publisher(book.getPublisher())
                .publishedDate(book.getPublishedDate())
                .language(book.getLanguage())
                .pages(book.getPages())
                .price(book.getPrice())
                .coverImageUrl(book.getCoverImageUrl())
                .active(book.isActive())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
        return dto;
    }

    public Book toEntity(BookDTO dto) throws BookException {
        if(dto==null){
            return null;
        }
        Book book=new  Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setAuthor(dto.getAuthor());

        //Map genre - fetch from database using genreId
        if(dto.getGenreId()!=null){
            Genre genre=genreRepository.findById(dto.getGenreId()).orElseThrow(()->new BookException("Genre not found"));
        }
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setTotalCopies(dto.getTotalCopies());
        book.setPrice(dto.getPrice());
        book.setCoverImageUrl(dto.getCoverImageUrl());
        book.setLanguage(dto.getLanguage());
        book.setPublishedDate(dto.getPublishedDate());
        book.setPages(dto.getPages());
        book.setPublisher(dto.getPublisher());
        book.setDescription(dto.getDescription());

        return book;
    }
    //dto m humne likha and entity m daala
    public void updateEntityFromDTO(BookDTO dto,Book book) throws Exception{
        if(dto==null|| book==null){
            return;
        }
        //ISBN should not be updated
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        //update genre if provided
        if(dto.getGenreId()!=null){
            Genre genre=genreRepository.findById(dto.getGenreId()).orElseThrow(()->new BookException("Genre with id:"+dto.getGenreId()+" not found"));
            book.setGenre(genre);
        }
        book.setPublishedDate(dto.getPublishedDate());
        book.setLanguage(dto.getLanguage());
        book.setPages(dto.getPages());
        book.setPrice(dto.getPrice());
        book.setCoverImageUrl(dto.getCoverImageUrl());
        book.setDescription(dto.getDescription());
        if(dto.getActive()!=null){
            book.setActive(dto.getActive());
        }

    }
}
