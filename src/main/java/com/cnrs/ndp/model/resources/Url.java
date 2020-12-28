package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Url extends Resource {

    String dateCreationFichier;
    String lienInternet;
    String preparation;

}
