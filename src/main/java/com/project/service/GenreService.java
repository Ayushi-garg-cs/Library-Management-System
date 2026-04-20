package com.project.service;

import com.project.modal.Genre;
import com.project.payload.dto.GenreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    GenreDTO createGenre(GenreDTO genre);
    List<GenreDTO> getAllGenres();
    GenreDTO getGenreById(int genreId);
    GenreDTO updateGenre(Long genreId, GenreDTO genre);
    void deleteGenre(int genreId);
    void hardDeleteGenre(Long genreId);
    List<GenreDTO> getAllActiveGenresWithSubGenres();
    List<GenreDTO> getTopLevelGenres();
    Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable);
    long getTotalActiveGenres();
    long getBookCountByGenre(Long genreId)


}
