package com.cnrs.ndp.service;

import com.cnrs.ndp.model.Label;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ThesaurusService {


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



    public List<String> getListTermes(String terme, int indexGroupTravail) {

        if (indexGroupTravail != -1) {
            List<String> labels = new ArrayList<>();

            String[] parts = thesoNames.get(indexGroupTravail).split(splitTheso);

            for (int i=0; i<parts.length; i++) {
                try {
                    List<Label> list = Arrays.asList(new RestTemplate().getForObject(createUrl(terme, parts[i]), Label[].class));
                    if (!CollectionUtils.isEmpty(list)) {
                        for (Label label : list) {
                            labels.add(label.getLabel());
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
        return new StringBuffer(baseUrlTheso)
                .append(terme)
                .append("?theso=")
                .append(thesoName)
                .append("&group=")
                .append(groupe)
                .append("&lang=")
                .append(langue)
                .toString();
    }

}
