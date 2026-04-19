package com.project.payload.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotBlank(message="Genre code is mandatory")
    private String code;

    @NotBlank(message="Genre name is mandatory")
    private String name;

    @Size(max=500,message="description must not exceed 500 characters")
    private String description;

    @Min(value=0, message="display order cannot be negative")
    private Integer displayOrder=0;

    private Boolean active;
    private Long parentGenreId;
    private String parentGenreName;
    private List<GenreDTO> subGenres;
    private Long bookCount;//books inside that genre
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
