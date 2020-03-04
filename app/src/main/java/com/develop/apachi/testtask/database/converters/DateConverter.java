package com.develop.apachi.testtask.database.converters;

import android.arch.persistence.room.TypeConverter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateConverter {

    /**
     * Converts from {@link  DateTime} to long value.
     *
     * @param aDateTime
     *      Date time.
     * @return long value.
     */
    @TypeConverter
    public static long fromDateTime(DateTime aDateTime) {

        return aDateTime.getMillis();
    }

    /**
     * Converts from long value to {@link  DateTime} object.
     *
     * @param aValue
     *      long value.
     * @return DateTime object.
     */
    @TypeConverter
    public static DateTime fromLong(long aValue) {

        return new DateTime(aValue);
    }

    /**
     * Converts {@link DateTimeZone} to {@link String}
     *
     * @param aDateTimeZone
     *      Time zone.
     * @return String value.
     */
    @TypeConverter
    public static String fromDateTimeZone(DateTimeZone aDateTimeZone) {

        return aDateTimeZone.getID();
    }

    /**
     * Converts id to {@link DateTimeZone}.
     *
     * @param aId
     *      Id as {@link DateTimeZone#iID}
     * @return {@link DateTimeZone} instance with specified id.
     */
    @TypeConverter
    public static DateTimeZone fromString(String aId) {

        return DateTimeZone.forID(aId);
    }
}
