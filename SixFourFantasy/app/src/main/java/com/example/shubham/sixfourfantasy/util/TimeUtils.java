package com.example.shubham.sixfourfantasy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtils {


    private static final java.lang.String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final java.lang.String DISPLAY_TIME_FORMAT = "HH:mm z";
    private static final java.lang.String DISPLAY_DATE_FORMAT = "MMM dd, yyyy";

    public static boolean isInDaysInterval(String time, int days) throws ParseException {
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(getDate(time));
        Calendar now = Calendar.getInstance();
        return Math.abs(now.get(Calendar.DAY_OF_YEAR) - timeCal.get(Calendar.DAY_OF_YEAR)) <= days;
    }

    public static String getTimeWithZ(String startTime) throws ParseException {
        return  new SimpleDateFormat(DISPLAY_TIME_FORMAT).format(getDate(startTime));
    }

    public static String getDisplayDate(String startTime) throws ParseException {
        return  new SimpleDateFormat(DISPLAY_DATE_FORMAT).format(getDate(startTime));
    }

    private static Date getDate(String date) throws ParseException {
        return new SimpleDateFormat(DATE_FORMAT).parse(date.replaceAll("Z$", "+0000"));
    }
}
