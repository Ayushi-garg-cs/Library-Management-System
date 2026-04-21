package com.project.service.impl;

import com.project.exception.GenreException;
import com.project.mapper.GenreMapper;
import com.project.modal.Genre;
import com.project.payload.dto.GenreDTO;
import com.project.repository.GenreRepository;
import com.project.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreDTO createGenre(GenreDTO genreDTO) {
        Genre genre = genreMapper.toEntity(genreDTO);
        Genre savedGenre=genreRepository.save(genre);

        //GenreDTO dto= GenreMapper.toDTO(savedGenre);
       // return dto;
        return  genreMapper.toDTO(savedGenre);
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(genre->GenreMapper.toDTO(genre))
                .collect(Collectors.toList());
    }

    @Override
    public GenreDTO getGenreById(int genreId) throws GenreException {
        return null;
    }

    @Override
    public GenreDTO getGenreById(Long genreId) throws GenreException {
        Genre genre=genreRepository.findById( genreId).orElseThrow(
                ()->new GenreException("genre not found")
        );
        return genreMapper.toDTO(genre);
    }

    @Override
    public GenreDTO updateGenre(Long genreId, GenreDTO genre) {
        return null;
    }

    @Override
    public void deleteGenre(int genreId) {

    }

    @Override
    public void hardDeleteGenre(Long genreId) {

    }

    @Override
    public List<GenreDTO> getAllActiveGenresWithSubGenres() {
        return List.of();
    }

    @Override
    public List<GenreDTO> getTopLevelGenres() {
        return List.of();
    }

    @Override
    public Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable) {
        return null;
    }

    @Override
    public long getTotalActiveGenres() {
        return 0;
    }

    @Override
    public long getBookCountByGenre(Long genreId) {
        return 0;
    }
}
