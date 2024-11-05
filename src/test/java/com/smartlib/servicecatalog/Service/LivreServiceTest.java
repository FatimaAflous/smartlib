package com.smartlib.servicecatalog.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.smartlib.servicecatalog.Service.LivreService;
import com.smartlib.servicecatalog.Entity.Livre;
import com.smartlib.servicecatalog.Repository.LivreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class LivreServiceTest {

    @Mock
    private LivreRepository livreRepository;

    @InjectMocks
    private LivreService livreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLivre() {
        Livre livre = new Livre();
        livre.setTitre("Test Livre");

        when(livreRepository.save(any(Livre.class))).thenReturn(livre);

        Livre createdLivre = livreService.add_livre(livre);
        assertNotNull(createdLivre);
        assertEquals("Test Livre", createdLivre.getTitre());

        verify(livreRepository, times(1)).save(livre);
    }

    @Test
    void testShowLivre() {
        Long id = 17L;
        Livre livre = new Livre();
        livre.setId(id);
        livre.setTitre("Test Livre");

        when(livreRepository.findById(id)).thenReturn(Optional.of(livre));

        Optional<Livre> foundLivre = livreService.show_livre(id);
        assertTrue(foundLivre.isPresent());
        assertEquals("Test Livre", foundLivre.get().getTitre());

        verify(livreRepository, times(1)).findById(id);
    }
}
