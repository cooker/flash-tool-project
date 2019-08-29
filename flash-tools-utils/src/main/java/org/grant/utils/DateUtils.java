package org.grant.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.grant.utils.constants.DateFormats;

import java.text.ParseException;
import java.util.Date;

/**
 * grant
 * 23/8/2019 3:12 PM
 * 描述：
 */
public class DateUtils {

    public static String format(Date date, DateFormats format) {
        return DateFormatUtils.format(date, format.toString());
    }

    public static Date parse(String str, DateFormats format) throws ParseException {
        return org.apache.commons.lang3.time.DateUtils.parseDate(str, format.toString());
    }

}
