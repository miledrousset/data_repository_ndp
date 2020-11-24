package com.cnrs.ndp.outils;

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


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PdfManagementTest {

    private final static String IMG_PATH = "src/test/resources/file-sample.jpg";
    private final static String PDF_PATH = "classpath:file-sample.pdf";

    @Autowired
    private PdfManagement pdfManagement;


    @Test
    public void pdfTraitementTest() throws IOException {

        File pdfFile = ResourceUtils.getFile(PDF_PATH);

        if (new File(IMG_PATH).exists()) {
            new File(IMG_PATH).delete();
        }

        pdfManagement.pdfTraitement(pdfFile, IMG_PATH);

        if (!new File(IMG_PATH).exists()) {
            Assert.assertTrue(false);
        }

        new File(IMG_PATH).delete();

    }
}
