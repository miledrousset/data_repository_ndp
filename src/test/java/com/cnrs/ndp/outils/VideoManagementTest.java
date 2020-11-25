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


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class VideoManagementTest {

    private final static String IMG_PATH = "src/test/resources/SampleVideo.jpg";
    private final static String VIDEO_PATH = "classpath:SampleVideo.mp4";

    @Autowired
    private VideoManagement videoManagement;


    @Test
    public void videoTraitementTest() throws Exception {

        File videoFile = ResourceUtils.getFile(VIDEO_PATH);

        if (new File(IMG_PATH).exists()) {
            new File(IMG_PATH).delete();
        }

        videoManagement.videoTraitement(videoFile.getPath(), IMG_PATH);

        if (!new File(IMG_PATH).exists()) {
            Assert.assertTrue(false);
        }

        new File(IMG_PATH).delete();

    }

}
