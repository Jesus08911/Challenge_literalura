package com.alura.literalura.repository;


import com.alura.literalura.model.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autor")
    List<Libro> findAllWithAutor();

    @Query("SELECT l FROM Libro l JOIN FETCH l.autor WHERE l.idioma = :idioma")
    List<Libro> findLibrosPorIdioma(@Param("idioma") String idioma);
}
