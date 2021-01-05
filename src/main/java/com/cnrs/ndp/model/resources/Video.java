package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Video extends Resource {

    String media;
    Date dateCreationFichier;
    String lienInternet;
    String extension;
    String dateConsultation;
    Date dateCreationMp4;
    String preparation;
    String collecteur;

}
