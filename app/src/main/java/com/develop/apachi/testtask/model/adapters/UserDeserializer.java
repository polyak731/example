package com.develop.apachi.testtask.model.adapters;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.develop.apachi.testtask.model.User;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.lang.reflect.Type;
import java.util.TimeZone;

/**
 * Deserializer for users.
 */
public class UserDeserializer implements JsonDeserializer<User> {

    interface UserResponseContact {

        // name:
        String NAME_SECTION = "name";

        String TITLE = "title";
        String FIRST_NAME = "first";
        String LAST_NAME = "last";

        // location
        String LOCATION_SECTION = "location";

        String STREET = "street";
        String CITY = "city";
        String STATE = "state";
        String POST_CODE = "postcode";

        // Date of birth section
        String DOB_SECTION = "dob";

        String BIRTH_DATE = "date";
        String AGE = "age";

        // Registration section
        String REGISTRATION_SECTION = "registered";

        String REGISTRATION_DATE = "date";
        String REGISTRATION_AGE = "age";

        String PHONE_NUMBER = "phone";
        String CELL_NUMBER = "cell";

        String ID_SECTION = "id";

        String USER_ID = "value";
        String ID_TYPE = "name";

        String PICTURE_SECTION = "picture";

        String THUMBNAIL_PICTURE_URL = "thumbnail";
        String MEDIUM_PICTURE_URL = "medium";
        String LARGE_PICTURE_URL = "large";

        String TIME_ZONE_SECTION = "timezone";

        String TIME_ZONE = "offset";

        // Section with coordinates
        String GEOLOCATION_SECTION = "coordinates";

        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";

        // Section with login data
        String LOGIN_SECTION = "login";

        String USER_NAME = "username";

        String EMAIL = "email";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User deserialize(@NonNull JsonElement aJson,
                            @NonNull Type aTypeOfT,
                            @NonNull JsonDeserializationContext aContext) throws JsonParseException {

        final JsonObject jsonObject = aJson.getAsJsonObject();

        final JsonObject idSection = jsonObject.getAsJsonObject(UserResponseContact.ID_SECTION);

        final JsonObject nameSection = jsonObject.getAsJsonObject(UserResponseContact.NAME_SECTION);

        final JsonObject locationSection = jsonObject.getAsJsonObject(UserResponseContact.LOCATION_SECTION);

        final JsonObject timeZoneSection = locationSection.getAsJsonObject(UserResponseContact.TIME_ZONE_SECTION);

        final DateTimeZone timeZone = DateTimeZone.forTimeZone(
                TimeZone.getTimeZone("GMT" + timeZoneSection.get(UserResponseContact.TIME_ZONE).getAsString()));

        final JsonObject geoLocationSection = locationSection.getAsJsonObject(UserResponseContact.GEOLOCATION_SECTION);

        final Location location = new Location(this.getClass().getName());
        location.setLongitude(geoLocationSection.get(UserResponseContact.LONGITUDE).getAsDouble());
        location.setLatitude(geoLocationSection.get(UserResponseContact.LATITUDE).getAsDouble());

        final JsonObject loginSection = jsonObject.getAsJsonObject(UserResponseContact.LOGIN_SECTION);

        final JsonObject dateOfBirthSection = jsonObject.getAsJsonObject(UserResponseContact.DOB_SECTION);

        final DateTime dateOfBirth = DateTime.parse(dateOfBirthSection.get(UserResponseContact.BIRTH_DATE).getAsString());

        final JsonObject registrationSection = jsonObject.getAsJsonObject(UserResponseContact.REGISTRATION_SECTION);

        final DateTime registrationTime = DateTime.parse(registrationSection.get(UserResponseContact.REGISTRATION_DATE).getAsString());

        final JsonObject pictureSection = jsonObject.getAsJsonObject(UserResponseContact.PICTURE_SECTION);

        return new User.Builder(extractValue(idSection, UserResponseContact.USER_ID))
                .setIdType(extractValue(idSection, UserResponseContact.ID_TYPE))
                .setTitle(extractValue(nameSection, UserResponseContact.TITLE))
                .setFirstName(extractValue(nameSection,UserResponseContact.FIRST_NAME))
                .setLastName(extractValue(nameSection, UserResponseContact.LAST_NAME))
                .setStreet(extractValue(locationSection, UserResponseContact.STREET))
                .setState(extractValue(locationSection, UserResponseContact.STATE))
                .setCity(extractValue(locationSection, UserResponseContact.CITY))
                .setPostCode(extractValue(locationSection, UserResponseContact.POST_CODE))
                .setUserTimeZone(timeZone)
                .setLocation(location)
                .setEmail(extractValue(jsonObject, UserResponseContact.EMAIL))
                .setUserName(extractValue(loginSection, UserResponseContact.USER_NAME))
                .setBirthDate(dateOfBirth)
                .setAge(dateOfBirthSection.get(UserResponseContact.AGE).getAsInt())
                .setRegistrationDate(registrationTime)
                .setRegistrationAge(registrationSection.get(UserResponseContact.REGISTRATION_AGE).getAsInt())
                .setPhoneNumber(jsonObject.get(UserResponseContact.PHONE_NUMBER).getAsString())
                .setCellNumber(jsonObject.get(UserResponseContact.CELL_NUMBER).getAsString())
                .setLargePicture(extractValue(pictureSection, UserResponseContact.LARGE_PICTURE_URL))
                .setMediumPicture(extractValue(pictureSection, UserResponseContact.MEDIUM_PICTURE_URL))
                .setThumbnailPicture(extractValue(pictureSection, UserResponseContact.THUMBNAIL_PICTURE_URL))
                .build();
    }

    /**
     * Retrieves user's id.
     *
     * @param aSection
     *      Section.
     * @return String with current id value.
     */
    @Nullable
    private String extractValue(@NonNull JsonObject aSection,
                                @NonNull String aSectionName) {

        final JsonElement object = aSection.get(aSectionName);

        if (checkJsonValid(object)) {

            return object.getAsString();
        } else {
            return null;
        }
    }

    /**
     * Checks is json is valid.
     *
     * @param aJsonElement
     *      Json object.
     * @return true if json object is valid.
     */
    private boolean checkJsonValid(JsonElement aJsonElement) {

        return !aJsonElement.isJsonNull();
    }
}
