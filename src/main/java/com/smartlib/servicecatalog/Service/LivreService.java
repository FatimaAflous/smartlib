package com.smartlib.servicecatalog.Service;

import com.smartlib.servicecatalog.Entity.Livre;
import com.smartlib.servicecatalog.Entity.Status;
import com.smartlib.servicecatalog.Repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.catalog.Catalog;
import java.util.List;
import java.util.Optional;
@Service
public class LivreService {
    @Autowired
    private LivreRepository livreRepository;

    //End points de base : CRUD
    //creer un livre
    public Livre add_livre(Livre catalog) {
        return livreRepository.save(catalog);
    }
    //modifier un livre
    public Livre update_livre(Long id, Livre new_livre) {
        Livre catalog1 = livreRepository.findById(id).orElseThrow(() -> new RuntimeException("walo makaynch"));
        if (catalog1 != null) {
            catalog1.setIsbn(new_livre.getIsbn());
            catalog1.setAuteur(new_livre.getAuteur());
            catalog1.setTitre(new_livre.getTitre());
            catalog1.setGenre(new_livre.getGenre());
            catalog1.setDescription(new_livre.getDescription());
            catalog1.setDataPublication(new_livre.getDataPublication());
            catalog1.setStatus(new_livre.getStatus()); // Ajouter cette ligne
            return livreRepository.save(catalog1);
        }
        return catalog1;
    }
    //supprimer un livre
    public void delete_livre(Long id) {
        livreRepository.deleteById(id);
    }
    //recuper un livre
    public Optional<Livre> show_livre(Long id) {
        return livreRepository.findById(id);
    }




    //End points pour filtrage et recherche:
    //recuperer tous les livres
    public List<Livre> show_all_livres(){
        return livreRepository.findAll();
    }

    //recuperer livre par titre
    public List<Livre> searchByTitle(String title) {
        return livreRepository.findByTitreContainingIgnoreCase(title);
    }
    //recuperer livre par genre
    public List<Livre> searchByGenre(String genre) {
        return livreRepository.findByGenreContainingIgnoreCase(genre);
    }
    //recuperer livre par auteur
    public List<Livre> searchByAuteur(String auteur) {
        return livreRepository.findByAuteurContainingIgnoreCase(auteur);
    }
    //recuperer livre par titre et auteur
    public List<Livre> filterByGenreAndAuthor(String genre, String auteur) {
        return livreRepository.findByGenreContainingIgnoreCaseAndAuteurContainingIgnoreCase(genre, auteur);
    }
    // Récupérer les livres par le début du titre
    public List<Livre> filterByTitlePrefix(String prefix) {
        return livreRepository.findByTitreStartingWithIgnoreCase(prefix);
    }

// gestion du status du livre
// Méthodes pour l'intégration avec d'autres microservices
public boolean checkDisponibilite(Long id) {
    Optional<Livre> livre = livreRepository.findById(id);
    if (livre.isPresent()) {
        return livre.get().getStatus() == Status.DISPONIBLE;
    } else {
        throw new RuntimeException("Livre non trouvé");
    }
}
    public Livre updateStatus(Long id, String status) {
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        if ("DISPONIBLE".equalsIgnoreCase(status)) {
            livre.setStatus(Status.DISPONIBLE);
        } else if ("EMPRUNTE".equalsIgnoreCase(status)) {
            livre.setStatus(Status.EMPRUNTE);
        } else {
            throw new IllegalArgumentException("Statut invalide");
        }
        return livreRepository.save(livre);
    }

}
