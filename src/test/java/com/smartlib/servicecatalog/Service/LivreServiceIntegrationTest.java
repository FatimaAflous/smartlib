package com.smartlib.servicecatalog.Service;
import com.smartlib.servicecatalog.Entity.Livre;
import com.smartlib.servicecatalog.Entity.Status;
import com.smartlib.servicecatalog.Service.LivreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // Assurez-vous que le contexte Spring est chargé
@ActiveProfiles("test") // Utilisez un profil de test si nécessaire
public class LivreServiceIntegrationTest {

    @Autowired
    private LivreService livreService;

    @BeforeEach
    public void setUp() {
        // Cette méthode peut être utilisée pour nettoyer la base de données avant chaque test
        // livreRepository.deleteAll(); // Assurez-vous que vous avez accès au repository ici si nécessaire
    }

    @Test
    public void testAddAndShowLivre() {
        // Compter les livres existants avant d'ajouter un nouveau livre
        int initialCount = livreService.show_all_livres().size();

        // Créer un nouveau livre à ajouter
        Livre livre = new Livre();
        livre.setTitre("Titre Test");
        livre.setAuteur("Auteur Test");
        livre.setGenre("Genre Test");
        livre.setDataPublication("2024-01-01");
        livre.setDescription("Description Test");
        livre.setIsbn("1234567890");
        livre.setStatus(Status.DISPONIBLE);

        // Ajouter le nouveau livre
        livreService.add_livre(livre);

        // Vérifier qu'il y a un livre de plus dans la base de données
        List<Livre> livres = livreService.show_all_livres();
        assertEquals(initialCount + 1, livres.size()); // Vérifie que le nombre total a été incrémenté

        // Vérifier que le nouveau livre a été correctement ajouté
        Livre addedLivre = livres.get(livres.size() - 1); // Récupérer le dernier livre ajouté
        assertEquals("Titre Test", addedLivre.getTitre());
        assertEquals("Auteur Test", addedLivre.getAuteur());
        assertEquals("Genre Test", addedLivre.getGenre());
    }

}
