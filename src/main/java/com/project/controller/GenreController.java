package com.project.controller;

import com.project.exception.GenreException;
import com.project.modal.Genre;
import com.project.payload.dto.GenreDTO;
import com.project.payload.response.ApiResponse;
import com.project.repository.GenreRepository;
import com.project.service.GenreService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genre){
        GenreDTO createdGenre=genreService.createGenre(genre);
        return ResponseEntity.ok(createdGenre);
    }

    @GetMapping
    public ResponseEntity<?> getAllGenres(){
        List<GenreDTO> genres=genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<?> getGenreById(@RequestParam("genreId") long genreId) throws GenreException {
        GenreDTO genres=genreService.getGenreById(genreId);
        return ResponseEntity.ok(genres);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<?> updateGenre(@RequestParam("genreId") long genreId, @RequestBody GenreDTO genre) throws GenreException {
        GenreDTO updatedGenre=genreService.updateGenre(genreId,genre);
        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<?>  deleteGenre(@RequestParam("genreId") long genreId) throws GenreException {
        genreService.deleteGenre((int) genreId);
        ApiResponse apiResponse=new ApiResponse("Genre Deleted-soft delete", true);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<?> hardDeleteGenre(@RequestParam("genreId") long genreId) throws GenreException{
        genreService.hardDeleteGenre((long) genreId);
        ApiResponse apiResponse=new ApiResponse("Genre Deleted-hard delete", true);
        return ResponseEntity.ok(apiResponse);
    }
}
