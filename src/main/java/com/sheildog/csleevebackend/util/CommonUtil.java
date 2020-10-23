package com.sheildog.csleevebackend.util;

import com.sheildog.csleevebackend.bo.PageCounter;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        int pageNum = start / count;
        PageCounter pageCounter = PageCounter.builder()
                .page(pageNum)
                .count(count)
                .build();
        return pageCounter;
    }

    public static Boolean isInTimeLine(Date date, Date start, Date end) {
        Long time = date.getTime();
        Long startTime = start.getTime();
        Long endTime = end.getTime();
        if (time > startTime && time < endTime) {
            return true;
        }
        return false;
    }

    public static Calendar addSomeSeconds(Calendar calendar, int seconds) {
        calendar.add(Calendar.SECOND, seconds);
        return calendar;
    }

    public static Boolean isOutOfDate(Date startTime, Long period) {
        long now = Calendar.getInstance().getTimeInMillis();
        Long startTimeStamp = startTime.getTime();
        Long periodMillSecond = period * 1000;
        return now > (startTimeStamp + periodMillSecond);
    }

    public static Boolean isOutOfDate(Date expiredTime) {
        long now = Calendar.getInstance().getTimeInMillis();
        long expiredTimeStamp = expiredTime.getTime();
        return now > expiredTimeStamp;
    }

    public static String yuanToFenPlainString(BigDecimal p) {
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

    public static String toPlain(BigDecimal p) {
        return p.stripTrailingZeros().toPlainString();
    }

    public static String timestamp10() {
        Long timestamp13 = Calendar.getInstance().getTimeInMillis();
        String timestamp13Str = timestamp13.toString();
        return timestamp13Str.substring(0, timestamp13Str.length() - 3);
    }
}
