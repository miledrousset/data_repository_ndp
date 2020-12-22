package com.cnrs.ndp.utils;

import com.cnrs.ndp.utils.StringUtils;
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
    public void removeSpecialCharacterTest() {
        String str = "Pr!ogr#am%m*in&g Lan?#guag(e";
        String strAfter = StringUtils.removeSpecialCharacter(str);
        Assert.assertEquals("Programming_Language", strAfter);
    }
    
    @Test
    public void formatFileNameTest() {
        String str = "Name File";
        String strAfter = StringUtils.formatFileName(str);
        Assert.assertEquals("Name_File", strAfter);
    }
    
}
