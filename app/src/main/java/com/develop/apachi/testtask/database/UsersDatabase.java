package com.develop.apachi.testtask.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.develop.apachi.testtask.database.converters.DateConverter;
import com.develop.apachi.testtask.database.converters.LocationConverter;
import com.develop.apachi.testtask.model.User;

/**
 * Database with users.
 */
@Database(entities = User.class, version = UsersDatabase.DATABASE_VERSION)
@TypeConverters({LocationConverter.class, DateConverter.class})
public abstract class UsersDatabase extends RoomDatabase {

    /*package*/ static final int DATABASE_VERSION = 1;

    /**
     * Retrieves users DAO,
     *
     * @return Users DAO.
     */
    public abstract UserDao getUserDao();
}
