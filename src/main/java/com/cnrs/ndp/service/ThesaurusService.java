package com.cnrs.ndp.service;

import com.cnrs.ndp.model.Label;

import java.util.List;


public interface ThesaurusService {

    List<Label> getListTermes(String terme, int indexGroupTravail);

}
