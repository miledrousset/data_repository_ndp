package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DonneeLaserConso extends Resource {

    String source;
    Date dateMiseDisposition;
    String couverture;
    String idSources;
    String logiciel;
    String methodeConsolidation;
    String systemeCoordonnees;

}
