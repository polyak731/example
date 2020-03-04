package com.develop.apachi.testtask.database;

/**
 * Database contract class to describe database schema.
 */
public interface UsersDatabaseContract {

    String DATABASE_NAME = "random_users_database.db";

    /**
     * Describes {@link UsersTable#TABLE_NAME} table schema.
     */
    interface UsersTable {

        String TABLE_NAME = "users";

        String _ID = "id";
        String USER_ID = "user_id";
        String TITLE = "title";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
        String USER_NAME = "user_name";
        String BIRTH_DATE = "birth_date";
        String AGE = "age";
        String REGISTRATION_DATE = "registration_date";
        String REGISTRATION_AGE = "registration_age";
        String PHONE_NUMBER = "phone_number";
        String CELL_NUMBER = "cell_number";
        String ID_TYPE = "id_type";
        String THUMBNAIL_PICTURE_URL = "thumbnail_picture_url";
        String MEDIUM_PICTURE_URL = "medium_picture_url";
        String LARGE_PICTURE_URL = "large_picture_url";
        String STREET = "street";
        String CITY = "city";
        String STATE = "state";
        String POST_CODE = "post_code";
        String LOCATION_BLOB = "location";
        String TIME_ZONE = "time_zone";
        String EMAIL = "email";
    }
}
