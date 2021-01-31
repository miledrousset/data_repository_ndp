package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeblinCore extends Resource {

    Date dateMiseDisposition;
    String source;
    Date couverture;
    
}
