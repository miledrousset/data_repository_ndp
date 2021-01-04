package com.cnrs.ndp.utils;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class StringUtilsTest {

    @Test
    public void formatFileNameTest() {

        String str1 = "Pr!ogr#am%m*in&g Lan?#guag(e";
        str1 = StringUtils.formatFileName(str1);
        Assert.assertEquals("Programming_Language", str1);

        String str = "Name File";
        String strAfter = StringUtils.formatFileName(str);
        Assert.assertEquals("Name_File", strAfter);
    }
    
}
