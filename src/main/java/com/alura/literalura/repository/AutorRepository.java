package com.alura.literalura.repository;


import com.alura.literalura.model.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();

    @Query("SELECT a FROM Autor a JOIN FETCH a.libros " +
            "WHERE YEAR(a.fechaNacimiento) <= :year " +
            "AND (YEAR(a.fechaFallecimiento) IS NULL OR YEAR(a.fechaFallecimiento) >= :year)")
    List<Autor> findAutoresVivosEnAnio(@Param("year") Integer year);
}

