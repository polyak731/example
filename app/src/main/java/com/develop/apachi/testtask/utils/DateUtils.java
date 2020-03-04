package com.develop.apachi.testtask.utils;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Utils class for date formatting.
 */
public class DateUtils {

    private static final String FORMAT = "dd:MM:YY";

    /**
     * Formats date in valid format.
     *
     * @param aDateTime
     *      Date time.
     * @return Formatted date string.
     */
    public static String fromDateTime(@NonNull DateTime aDateTime) {

        final DateTimeFormatter format = DateTimeFormat.forPattern(FORMAT);

        return format.print(aDateTime);
    }
}
