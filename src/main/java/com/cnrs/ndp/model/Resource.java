package com.cnrs.ndp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.io.Serializable;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Resource implements Serializable {
    
    boolean selected;
    File file;
    String nomFichier;
    String titre;
    String createur;
    String motsCles;
    String description;
    String editeur;
    String contributeur;
    String langue;
    String type;
    String format;
    String Support;
    String identifiantUnique;
    String relation;
    String gestionDesDroits;
    String citationBibliographie;

}
