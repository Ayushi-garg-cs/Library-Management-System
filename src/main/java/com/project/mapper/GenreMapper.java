package com.project.mapper;

import com.project.modal.Genre;
import com.project.payload.dto.GenreDTO;
import com.project.repository.GenreRepository;
import com.project.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreMapper {
    private final GenreRepository genreRepository;
    public static GenreDTO toDTO(Genre savedGenre){
        GenreDTO dto=GenreDTO.builder()
                .code(savedGenre.getCode())
                .name(savedGenre.getName())
                .description(savedGenre.getDescription())
                .displayOrder(savedGenre.getDisplayOrder())
                .active(savedGenre.isActive())
                .createdDate(savedGenre.getCreatedDate())
                .updatedDate(savedGenre.getUpdatedDate())
                .build();
        if(savedGenre.getParentGenre()!=null){
            dto.setParentGenreId(savedGenre.getParentGenre().getId());
            dto.setParentGenreName(savedGenre.getParentGenre().getName());
        }
        if(savedGenre.getSubGenres()!=null){
            dto.setSubGenres(savedGenre.getSubGenres().stream()
                    .filter(subGenre->subGenre.isActive())
                    .map(subGenre->toDTO(subGenre))
                    .collect(Collectors.toList()));
        }

        return dto;

    }

    public Genre toEntity(GenreDTO genreDTO){
        Genre genre=Genre.builder()
                .code(genreDTO.getCode())
                .name(genreDTO.getName())
                .description(genreDTO.getDescription())
                .displayOrder(genreDTO.getDisplayOrder())
                .active(true)
                .build();
        if(genreDTO.getParentGenreId()!=null){
            genreRepository.findById(genreDTO.getParentGenreId()).ifPresent(genre::setParentGenre);
        }
        return genre;
    }

}
