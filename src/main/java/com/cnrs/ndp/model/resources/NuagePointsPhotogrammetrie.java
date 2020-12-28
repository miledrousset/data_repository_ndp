package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NuagePointsPhotogrammetrie extends Resource {

    String dateMiseDisposition;
    String source;
    String couverture;
    String idSources;
    String logicielTraitement;
    String densitePointsMoyenne;
    String systemeCoordonnees;
    String architectureFichier;

}
