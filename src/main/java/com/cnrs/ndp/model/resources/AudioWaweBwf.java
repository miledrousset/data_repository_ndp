package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AudioWaweBwf extends Resource {


    String source;
    String couverture;
    String icrd;
    String ignr;
    String originatorReference;
    String originationDate;
    String originationTime;
    String timeReferenceTranslated;
    String timeReference;
    String bextVersion;
    String codingHistory;
    String iarl;
    String icmt;
    String ieng;
    String imed;
    String isft;

}
