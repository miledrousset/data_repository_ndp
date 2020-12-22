package com.cnrs.ndp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtils {

    private final static String DIRECTORY_NAME = "dd-MM-yyyy";


    public static String formatDateToString(Date date) {
        DateFormat formatter = new SimpleDateFormat(DIRECTORY_NAME);
        return formatter.format(date);
    }

    public static Date formatStringToDate(String dateInString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy", Locale.FRANCE);
            return formatter.parse(dateInString);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static String getDateTime(String formatString) {
        try {
            DateFormat formatter = new SimpleDateFormat(formatString);
            return formatter.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            return "";
        }
    }

}
