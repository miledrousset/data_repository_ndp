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

    @Value("${opentheso.theso_name}")
    private String thesoName;

    @Value("${opentheso.langue}")
    private String langue;

    @Value("${opentheso.groupe}")
    private String groupe;



    public List<String> getListTermes(String terme) {

        List<String> labels = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        List<Label> list = Arrays.asList(restTemplate.getForObject(createUrl(terme), Label[].class));

        if (!CollectionUtils.isEmpty(list)) {
            for (Label label : list) {
                labels.add(label.getLabel());
            }
        }

        return labels;

    }

    private String createUrl(String terme) {
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
