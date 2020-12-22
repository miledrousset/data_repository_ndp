package com.cnrs.ndp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of= {"nom", "champsList"})
@ToString(of= {"nom","champsList"})
public class Schemas {

    String nom;
    List<Champs> champsList;

}
