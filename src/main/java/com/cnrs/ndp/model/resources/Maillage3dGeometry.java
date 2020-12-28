package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Maillage3dGeometry extends Resource {

    String axeOrientation;
    String axeVertical;
    String uniteMesure;
    String logicielTraitement;
    String dimensionX;
    String dimensionY;
    String dimensionZ;
    String cheminFichier;
    String createur;
    String dateFichier;
    String formatFichier;
    String description;
    String encodage;

}
