package com.cnrs.ndp.model.resources;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AudioWaweBwf extends Resource {


    String source;
    String couverture;
    Date icrd;
    String ignr;
    String originatorReference;
    Date originationDate;
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
