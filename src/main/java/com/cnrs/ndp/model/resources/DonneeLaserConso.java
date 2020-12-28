package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DonneeLaserConso extends Resource {

    String source;
    String dateMiseDisposition;
    String couverture;
    String idSources;
    String logiciel;
    String methodeConsolidation;
    String systemeCoordonnees;

}
