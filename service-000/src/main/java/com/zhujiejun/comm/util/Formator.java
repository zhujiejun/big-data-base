package com.zhujiejun.comm.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DecimalFormat;
import java.text.Format;
import java.time.Instant;
import java.util.Date;

public class Formator {
    public final static String PATTERN001 = "000000000";
    public final static String PATTERN002 = "yyyyMMdd";

    public static String decimal(int original, String pattern) {
        Format formator = new DecimalFormat(pattern);
        return formator.format(original);
    }

    public static String now(String pattren) {
        return DateFormatUtils.format(Date.from(Instant.now()), pattren);
    }
}
