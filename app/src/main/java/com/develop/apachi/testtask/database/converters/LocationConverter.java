package com.develop.apachi.testtask.database.converters;

import android.arch.persistence.room.TypeConverter;
import android.location.Location;

import com.develop.apachi.testtask.database.UsersDatabaseContract;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Converter for locations.
 */
public class LocationConverter {

    /**
     * Converts from blob to location.
     *
     * @param aLocation
     *      Location.
     * @return Location.
     */
    @TypeConverter
    public static Location fromBlob(byte[] aLocation) {

        try {
            final ObjectInputStream objectInputStream = new ObjectInputStream(
                    new ByteArrayInputStream(aLocation));

            final double latitude = objectInputStream.readDouble();
            final double longitude = objectInputStream.readDouble();

            final Location location = new Location(UsersDatabaseContract.DATABASE_NAME);

            location.setLatitude(latitude);
            location.setLongitude(longitude);

            return location;
        } catch (IOException aEx) {

            return new Location(aEx.getMessage());
        }
    }

    /**
     * Converts location to array of bytes.
     *
     * @param aLocation
     *      Location from bytes.
     * @return array of bytes with location data.
     */
    @TypeConverter
    public static byte[] fromLocation(Location aLocation) {

        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

             final ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                     byteArrayOutputStream)) {

            objectOutputStream.writeDouble(aLocation.getLatitude());
            objectOutputStream.writeDouble(aLocation.getLongitude());

            return byteArrayOutputStream.toByteArray();
        } catch (IOException aEx) {

            return new byte[16];
        }
    }
}
