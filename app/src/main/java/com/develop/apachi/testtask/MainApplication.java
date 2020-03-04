package com.develop.apachi.testtask;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.develop.apachi.testtask.database.UsersDatabase;
import com.develop.apachi.testtask.database.UsersDatabaseContract;
import com.develop.apachi.testtask.model.UserRepository;
import com.develop.apachi.testtask.network.IRandomUserClient;
import com.develop.apachi.testtask.network.RandomUsersClientImpl;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Main application/DI container.
 */
public class MainApplication extends Application {

    /** Only one place where DB instance is created.
        main thread queries are disabled. */
    @NonNull
    private UsersDatabase iRoomDatabase;

    /** Client for HTTP activity*/
    @NonNull
    private IRandomUserClient iRandomUserClient;

    /** Repository pattern for users data.*/
    @Nullable
    private UserRepository iUserRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        iRoomDatabase = Room
                .databaseBuilder(new ContextWrapper(this),
                        UsersDatabase.class,
                        UsersDatabaseContract.DATABASE_NAME)
                .build();

        iRandomUserClient
                = new RandomUsersClientImpl(BuildConfig.SERVER_URL);

        iUserRepository = new UserRepository(
                new ContextWrapper(this),
                iRoomDatabase,
                iRandomUserClient
        );
    }

    /**
     * @return User repository instance.
     */
    @Nullable
    public UserRepository getUserRepository() {
        return iUserRepository;
    }
}
