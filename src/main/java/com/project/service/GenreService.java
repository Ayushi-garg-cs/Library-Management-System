package com.project.service;

import com.project.modal.Genre;
import com.project.payload.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    GenreDTO createGenre(GenreDTO genre);
    List<GenreDTO> getAllGenres();
}
