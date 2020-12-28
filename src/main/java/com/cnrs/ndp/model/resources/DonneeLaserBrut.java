package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DonneeLaserBrut extends Resource {

    String source;
    String dateMiseDisposition;
    String nuage;
    String couverture;
    String materiel;
    String methodeMetrologique;
    String resolutionAngulaire;
    String resolutionSpatiale;
    String densitePointMoyenne;
    String champHorizontalStation;
    String champVerticalStation;

}
