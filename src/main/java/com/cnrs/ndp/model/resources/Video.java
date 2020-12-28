package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Video extends Resource {

    String media;
    String dateCreationFichier;
    String lienInternet;
    String extension;
    String dateConsultation;
    String dateCreationMp4;
    String preparation;
    String collecteur;

}
