package com.smartlib.servicecatalog.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titre;
    private String auteur;
    private String genre;
    private String dataPublication;
    private String description;
    private String isbn;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Livre(String s, String s1, String s2, String s3, String s4, String s5, Status disponible) {
        this.titre=s ;
        this.auteur=s1;
        this.genre=s2;
        this.description=s3;
        this.isbn=s4;
        this.dataPublication=s5;
        this.status=Status.DISPONIBLE;

    }
}

