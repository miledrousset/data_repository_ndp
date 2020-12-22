package com.cnrs.ndp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of= {"nom","contenu", "exemple", "format", "libre", "obligatoire", "listeDeroulante"})
@ToString(of= {"nom","contenu", "exemple", "format", "libre", "obligatoire", "listeDeroulante"})
public class Champs {

    String nom;
    String contenu;
    String exemple;
    String format;
    boolean libre;
    boolean obligatoire;
    boolean listeDeroulante;

}
