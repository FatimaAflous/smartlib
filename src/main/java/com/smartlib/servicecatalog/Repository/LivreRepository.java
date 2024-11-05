package com.smartlib.servicecatalog.Repository;

import com.smartlib.servicecatalog.Entity.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivreRepository extends JpaRepository<Livre,Long> {
    List<Livre> findByTitreContainingIgnoreCase(String titre);
    List<Livre> findByAuteurContainingIgnoreCase(String auteur);
    List<Livre> findByGenreContainingIgnoreCase(String genre);
    List<Livre> findByGenreContainingIgnoreCaseAndAuteurContainingIgnoreCase(String genre, String auteur);
    List<Livre> findByTitreStartingWithIgnoreCase(String prefix);
}
