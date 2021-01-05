package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NuagePointsPhotogrammetrie extends Resource {

    Date dateMiseDisposition;
    String source;
    String couverture;
    String idSources;
    String logicielTraitement;
    String densitePointsMoyenne;
    String systemeCoordonnees;
    String architectureFichier;

}
