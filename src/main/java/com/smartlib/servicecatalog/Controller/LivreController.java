package com.smartlib.servicecatalog.Controller;

import com.smartlib.servicecatalog.Entity.Livre;
import com.smartlib.servicecatalog.Entity.Status;
import com.smartlib.servicecatalog.Service.LivreService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalog/api/v1")
@OpenAPIDefinition(
        info = @Info(
                title = "Gestion des Livres",
                description = "Gérer les livres et leurs informations",
                version = "1.0.0"
        ),
        servers = @Server(
                url = "http://localhost:8080/"
        )
)
public class LivreController {

    @Autowired
    private LivreService livreService;

    // Ajouter un livre
    @PostMapping("/livre")
    @Operation(
            summary = "Ajouter un livre",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Livre.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ajouté avec succès",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Livre.class))),
                    @ApiResponse(responseCode = "400", description = "Erreur de validation des données"),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur")
            }
    )
    public ResponseEntity<Livre> addLivre(@RequestBody Livre catalog) {
        Livre newCatalog = livreService.add_livre(catalog);
        return new ResponseEntity<>(newCatalog, HttpStatus.CREATED);
    }

    // Modifier un livre
    @PutMapping("/livre/{id}")
    @Operation(
            summary = "Mettre à jour un livre par son ID",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Livre.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Livre mis à jour avec succès",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Livre.class))),
                    @ApiResponse(responseCode = "404", description = "Livre non trouvé")
            }
    )
    public ResponseEntity<Livre> updateLivre(@PathVariable Long id, @RequestBody Livre updatedLivre) {
        Livre livre = livreService.update_livre(id, updatedLivre);
        return ResponseEntity.ok(livre);
    }

    // Supprimer un livre
    @DeleteMapping("/livre/{id}")
    @Operation(
            summary = "Supprimer un livre par son ID",
            parameters = @Parameter(name = "id", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Livre supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Livre non trouvé")
            }
    )
    public ResponseEntity<Void> deleteLivre(@PathVariable Long id) {
        livreService.delete_livre(id);
        return ResponseEntity.noContent().build();
    }

    // Récupérer un livre par ID
    @GetMapping("/livre/{id}")
    @Operation(
            summary = "Récupérer un livre par son ID",
            parameters = @Parameter(name = "id", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Livre récupéré avec succès",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Livre.class))),
                    @ApiResponse(responseCode = "404", description = "Livre non trouvé")
            }
    )
    public ResponseEntity<Livre> getLivre(@PathVariable Long id) {
        Optional<Livre> livre = livreService.show_livre(id);
        return livre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Récupérer tous les livres
    @GetMapping("/livres")
    @Operation(
            summary = "Récupérer la liste des livres",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Succès",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Livre.class))),
                    @ApiResponse(responseCode = "400", description = "Erreur dans la requête")
            }
    )
    public ResponseEntity<List<Livre>> getAllLivres() {
        List<Livre> livres = livreService.show_all_livres();
        return ResponseEntity.ok(livres);
    }

    // Rechercher un livre par titre
    @GetMapping("/search")
    @Operation(
            summary = "Rechercher un livre par titre",
            parameters = @Parameter(name = "title", required = true, description = "Titre du livre à rechercher"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des livres trouvés",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Livre.class))),
                    @ApiResponse(responseCode = "404", description = "Aucun livre trouvé")
            }
    )
    public ResponseEntity<List<Livre>> searchByTitle(@RequestParam String title) {
        List<Livre> livres = livreService.searchByTitle(title);
        return ResponseEntity.ok(livres);
    }

    // Filtrer les livres par auteur
    @GetMapping("/filtre/auteur")
    @Operation(
            summary = "Filtrer les livres par auteur",
            parameters = @Parameter(name = "auteur", required = true, description = "Nom de l'auteur pour le filtrage"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des livres filtrés par auteur",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Livre.class))),
                    @ApiResponse(responseCode = "404", description = "Aucun livre trouvé")
            }
    )

    public ResponseEntity<List<Livre>> filterByAuthor(@RequestParam String auteur) {
        List<Livre> livres = livreService.searchByAuteur(auteur);
        return ResponseEntity.ok(livres);
    }

    // Filtrer les livres par genre
    @GetMapping("/filtre/genre")
    @Operation(
            summary = "Filtrer les livres par genre",
            parameters = @Parameter(name = "genre", required = true, description = "Genre des livres à filtrer"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des livres filtrés par genre",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Livre.class))),
                    @ApiResponse(responseCode = "404", description = "Aucun livre trouvé")
            }
    )
    public ResponseEntity<List<Livre>> filterByGenre(@RequestParam String genre) {
        List<Livre> livres = livreService.searchByGenre(genre);
        return ResponseEntity.ok(livres);
    }

    // Filtre combiné (genre et auteur)
    @GetMapping("/filtre")
    @Operation(
            summary = "Filtrer les livres par genre et auteur",
            parameters = {
                    @Parameter(name = "genre", required = true, description = "Genre des livres à filtrer"),
                    @Parameter(name = "auteur", required = true, description = "Nom de l'auteur pour le filtrage")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des livres filtrés par genre et auteur",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Livre.class))),
                    @ApiResponse(responseCode = "404", description = "Aucun livre trouvé")
            }
    )
    public ResponseEntity<List<Livre>> filterByGenreAndAuthor(@RequestParam String genre, @RequestParam String auteur) {
        List<Livre> livres = livreService.filterByGenreAndAuthor(genre, auteur);
        return ResponseEntity.ok(livres);
    }
    @GetMapping("/livres/filter")
    @Operation(summary = "Filtrer les livres par préfixe de titre",
            description = "Récupère une liste de livres dont le titre commence par le préfixe spécifié.")
    @Parameter(description = "Le préfixe du titre des livres à filtrer", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de livres récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public List<Livre> filterLivresByTitlePrefix(@RequestParam String prefix) {
        return livreService.filterByTitlePrefix(prefix);
    }
    // Vérifier la disponibilité d’un livre
    @GetMapping("/livre/{id}/disponibilite")
    public ResponseEntity<Boolean> checkDisponibilite(@PathVariable Long id) {
        boolean isAvailable = livreService.checkDisponibilite(id);
        return ResponseEntity.ok(isAvailable);
    }

    // Mettre à jour le statut d'un livre
    @PatchMapping("/livre/{id}/status")
    @Operation(
            summary = "Mettre à jour le statut d'un livre",
            parameters = {
                    @Parameter(name = "id", required = true, description = "ID du livre dont on veut mettre à jour le statut"),
                    @Parameter(name = "status", required = true, description = "Nouveau statut du livre")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Statut mis à jour avec succès",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Livre.class))),
                    @ApiResponse(responseCode = "404", description = "Livre non trouvé")
            }
    )
    public ResponseEntity<Livre> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Livre livre = livreService.updateStatus(id, status);
        return ResponseEntity.ok(livre);
    }

    // Suggestions de livres par genre
    /*@GetMapping("/recommandations")
    public ResponseEntity<List<Livre>> getRecommendationsByGenre(@RequestParam String genre) {
        List<Livre> recommendations = livreService.getRecommendationsByGenre(genre);
        return ResponseEntity.ok(recommendations);
    }
     */
}
