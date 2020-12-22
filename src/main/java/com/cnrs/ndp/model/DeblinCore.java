package com.cnrs.ndp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeblinCore extends Resource {

    Date dateMiseDisposition;
    String source;
    String couverture;
    
}
