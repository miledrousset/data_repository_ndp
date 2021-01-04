package com.cnrs.ndp.service;

import com.cnrs.ndp.model.resources.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ReadMetadonneServiceTest {


    @Autowired
    private MetadonneService readMetadonneService;


    @Test
    public void createDeblinCoreRepportTest() throws IOException {

        File file = ResourceUtils.getFile("classpath:test_metadonne.xlsx");
        List<Resource> list = readMetadonneService.readDeblinCoreMetadonne(file, "1");

        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());

        Assert.assertEquals("Titre_test", list.get(0).getTitre());
        Assert.assertEquals("Mots", list.get(0).getMotsCles().get(0));
        Assert.assertEquals("Langue", list.get(0).getLangue());
        Assert.assertEquals("Type", list.get(0).getType());
    }

}
