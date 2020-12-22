package com.cnrs.ndp.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class DateUtilsTest {


    @Test
    public void formatDateToStringTest(){
        String dateString = DateUtils.formatDateToString(new Date());
        Assert.assertNotNull(dateString);
    }

    @Test
    public void formatStringToDateTest(){
        Date dateString = DateUtils.formatStringToDate("22/11/2020");
        Assert.assertNotNull(dateString);
    }

}
