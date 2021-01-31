package com.cnrs.ndp.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Table(name = "depots")
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Depots {

    @Id           
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id; 
    
    String nomDepot;
    
    Date dateDepot;

    String source;
    
    String groupeTravail;
    
    String repertoir;
    
    String depotHumaNum;
    
    String archeodrid;

    boolean status;

}
