package com.example.shubham.sixfourfantasy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtils {


    private static final java.lang.String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static boolean isInAMonth(String time) throws ParseException {
        Date date = new SimpleDateFormat(DATE_FORMAT).parse(time);
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(date);
        Calendar now = Calendar.getInstance();
        return Math.abs(now.get(Calendar.DAY_OF_YEAR) - timeCal.get(Calendar.DAY_OF_YEAR)) <= 31;
    }
}
