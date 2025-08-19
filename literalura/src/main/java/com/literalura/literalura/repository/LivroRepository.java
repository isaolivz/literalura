package com.literalura.literalura.repository;

import com.literalura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTitulo(String titulo);

    List<Livro> findByIdioma(String idioma);

    @Query("SELECT COUNT(l) FROM Livro l WHERE l.idioma = :idioma")
    Long contarLivrosPorIdioma(String idioma);
}