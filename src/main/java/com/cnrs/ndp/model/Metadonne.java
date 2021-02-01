package com.cnrs.ndp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Metadonne {

    String nom;
    String csvFilePath;
    String xslFilePath;
    String formatsAutorisees;

}
