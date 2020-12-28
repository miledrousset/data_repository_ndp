package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Maillage3dPhotogrammetrie extends Resource {

    String idSources;
    String methodeAssemblageNuagesPoints;
    String pourcentageDecimation;
    String algorithmeUtiliseMaillage;
    String methodeTexturage;
    String projectionTexture;
    String sourcesFichiersTexture;

}
