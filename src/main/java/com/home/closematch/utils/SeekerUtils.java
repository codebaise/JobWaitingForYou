package com.home.closematch.utils;

import java.util.Date;

public class SeekerUtils {
    public static int calculateYear(Date preTime, Date postTime) {
        Long val = ((postTime.getTime() - preTime.getTime()) / (24 * 60 * 60 * 1000)) / 365;
        return Integer.parseInt(String.valueOf(val));
    }
}
