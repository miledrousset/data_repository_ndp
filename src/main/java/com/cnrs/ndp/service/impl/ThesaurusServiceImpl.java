package com.cnrs.ndp.service.impl;

import com.cnrs.ndp.model.Label;
import com.cnrs.ndp.service.ThesaurusService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ThesaurusServiceImpl implements ThesaurusService {


    @Value("${opentheso.base_url}")
    private String baseUrlTheso;

    @Value("#{'${opentheso.theso_name}'.split(';')}")
    private List<String> thesoNames;

    @Value("${opentheso.split_theso}")
    private String splitTheso;

    @Value("${opentheso.langue}")
    private String langue;

    @Value("${opentheso.groupe}")
    private String groupe;



    public List<Label> getListTermes(String terme, int indexGroupTravail) {

        if (indexGroupTravail != -1) {
            List<Label> labels = new ArrayList<>();
            String[] parts = thesoNames.get(indexGroupTravail).split(splitTheso);
            for (int i=0; i<parts.length; i++) {
                try {
                    List<Label> list = Arrays.asList(new RestTemplate().getForObject(createUrl(terme, parts[i]), Label[].class));
                    if (!CollectionUtils.isEmpty(list)) {
                        for (Label label : list) {
                            labels.add(label);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Erreur dans la lecturre d'Opentheso !");
                }
            }

            return labels;
        } else {
            return new ArrayList<>();
        }
    }

    private String createUrl(String terme, String thesoName) {

        StringBuffer url = new StringBuffer(baseUrlTheso)
                .append(terme)
                .append("?theso=")
                .append(thesoName);

        if(!StringUtils.isEmpty(groupe)) {
            url.append("&group=").append(groupe);
        }

        url.append("&lang=").append(langue);

        return url.toString();
    }

}
