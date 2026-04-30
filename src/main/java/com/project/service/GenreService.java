package com.project.service;

import com.project.exception.GenreException;
import com.project.modal.Genre;
import com.project.payload.dto.GenreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    GenreDTO createGenre(GenreDTO genre);
    List<GenreDTO> getAllGenres();
    GenreDTO getGenreById(int genreId) throws GenreException;

    GenreDTO getGenreById(Long genreId) throws GenreException;

    GenreDTO updateGenre(Long genreId, GenreDTO genre) throws GenreException;
    void deleteGenre(int genreId) throws GenreException;
    void hardDeleteGenre(Long genreId) throws GenreException;
    List<GenreDTO> getAllActiveGenresWithSubGenres();
    List<GenreDTO> getTopLevelGenres();
    Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable);
    long getTotalActiveGenres();
    long getBookCountByGenre(Long genreId);


}
