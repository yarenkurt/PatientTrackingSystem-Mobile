package com.example.patienttrackerapp.helpers;


import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Helper {

    //region Helper Methods

    @SuppressLint("SimpleDateFormat")
    public static Date jsonStringToDate(String source) {
        try {
            source = source.substring(0, 19).replace("T", " ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(source);
        } catch (ParseException ex) {
            return null;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static Date stringToDateTime(String source) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(source);
        } catch (ParseException ex) {
            return null;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static Date stringToDate(String source) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(source);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String dateTimeToString(Date source) {
        try {
            return DateFormat.format("yyyy-MM-dd HH:mm", source).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateToString(Date source) {
        try {
            return DateFormat.format("yyyy-MM-dd", source).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String timeToString(Date source) {
        try {
            return DateFormat.format("HH:mm", source).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static Date stringToTime(String source) {
        try {
            String result = "1900-01-01 " + source;
            return stringToDateTime(result);
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean stringToBoolean(String source) {
        source=source.toLowerCase();
        return source.equals("1") || source.equals("true");
    }

    public static Integer stringToInt(String source) {
        return Integer.parseInt(source);
    }

    public static Double stringToDouble(String source) {
        return Double.parseDouble(source);
    }

//endregion
}
