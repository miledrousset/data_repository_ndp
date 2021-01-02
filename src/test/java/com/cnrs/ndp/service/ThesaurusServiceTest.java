package com.cnrs.ndp.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ThesaurusServiceTest {

    @Autowired
    private ThesaurusService thesaurusService;


    @Test
    public void getListTermesTest() {


        List<String> terme = thesaurusService.getListTermes("tes");
        Assert.assertNotNull(terme);
    }

}
