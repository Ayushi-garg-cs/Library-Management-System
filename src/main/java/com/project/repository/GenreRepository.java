package com.project.repository;

import com.project.modal.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre,Long> {
    //get all active genres with sub genres
    List<Genre> findByActiveTrueOrderByDisplayOrderAsc();
    //top level genre
    List<Genre> findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();
    //give all sub genres
    List<Genre> findByParentGenreIdAndActiveTrueOrderByDisplayOrderAsc(Long parentGenreId);
    //count total active genre
    long countByActiveTrue();
    //count book by genre
    // @Query("select count(b) from book b where b.genre.id=:genreId")
    // long countBooksByGenre(@Param("genreId") Long  genreId);

}
