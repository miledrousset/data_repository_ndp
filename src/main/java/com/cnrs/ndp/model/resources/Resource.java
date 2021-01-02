package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.io.Serializable;
import java.util.List;


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
    String motCle;
    List<String> motsCles;
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
    String notesInternes;
    String citationBibliographie;

}
