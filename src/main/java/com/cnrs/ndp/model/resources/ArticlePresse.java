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
    String collecteur;
    String preparation;
    String lienInternet;
    String relationLien;
    Date dateConsultation;
    Date dateCreationPDF;
    Date dateCreationFichier;
    
}
