package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Image extends Resource {

    Date dateCreation;
    String supportOriginalFichier;
    String extension;
    Date dateMiseDisposition;
    String fileSize;
    String model;
    String imageSize;
    String quality;
    String focalLength;
    String shutterSpeed;
    String aperture;
    String iso;
    String whiteBalance;
    String flash;
    String xResolution;
    String yResolution;
    String preservedFileName;

}
