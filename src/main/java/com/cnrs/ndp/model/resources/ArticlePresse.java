package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticlePresse extends Resource {
    
    String media;
    String createur;
    String extension;
    String lienInternet;
    String dateConsultation;
    String relationLien;
    String dateCreationPDF;
    String dateCreationFichier;
    String preparation;
    String collecteur;
    
}
