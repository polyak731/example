package com.develop.apachi.testtask.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.develop.apachi.testtask.model.User;

import java.util.List;

import io.reactivex.Single;

/**
 * Data access object for users.
 */
@Dao
public interface UserDao {

    /**
     * Retrieves all users from database ordered by last and first name.
     */
    @Query("SELECT * FROM "
            + UsersDatabaseContract.UsersTable.TABLE_NAME
            + " ORDER BY "
            + UsersDatabaseContract.UsersTable.LAST_NAME
            + ", "
            + UsersDatabaseContract.UsersTable.FIRST_NAME)
    Single<List<User>> getUsers();

    /**
     * Retrieves single user from database.
     *
     * @param aId
     *      Id of user.
     * @return Stream with single user or error if no user in database.
     */
    @Query("SELECT * FROM "
            + UsersDatabaseContract.UsersTable.TABLE_NAME
            + " WHERE "
            + UsersDatabaseContract.UsersTable._ID + " = " + ":aId")
    Single<User> getUser(int aId);

    /**
     * Saves users into the database.
     *
     * @param aUsers
     *      List of users.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveUsers(List<User> aUsers);

    /**
     * Clears all data from users table.
     */
    @Query("DELETE FROM " + UsersDatabaseContract.UsersTable.TABLE_NAME)
    void deleteAll();
}
