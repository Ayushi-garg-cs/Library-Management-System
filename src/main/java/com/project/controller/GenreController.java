package com.project.controller;

import com.project.modal.Genre;
import com.project.repository.GenreRepository;
import com.project.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre){
        Genre createdGenre=genreService.createGenre(genre);
        return ResponseEntity.ok(createdGenre);
    }
}
