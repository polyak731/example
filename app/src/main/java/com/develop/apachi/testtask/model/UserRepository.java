package com.develop.apachi.testtask.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.develop.apachi.testtask.database.UserDao;
import com.develop.apachi.testtask.database.UsersDatabase;
import com.develop.apachi.testtask.network.IRandomUserClient;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Repository class to query persons from specific source depending on data availability.
 */
@AnyThread
public class UserRepository {

    private static final String NETWORK_PREFERENCES = "net_pref";
    private static final String PREVIOUS_NETWORK_FETCH_KEY = "prev_fetch";
    /** Time without whose data is detected as stale.*/
    private static final Duration MAXIMUM_STALE_DATA_INTERVAL = new Duration(60 * 60 * 60);

    /** Cache of users.*/
    @NonNull
    private final List<User> iCache;

    /** Data access to users in database.*/
    @NonNull
    private final UserDao iUserDao;

    /** REST Api service.*/
    @NonNull
    private final IRandomUserClient iNetworkClient;

    /** Shared preferences to handle preferences.*/
    @NonNull
    private final SharedPreferences iSharedPreferences;

    /** Previous time of fetching data.*/
    @Nullable
    private Instant iPreviousNetworkFetchTime;

    /**
     * User repository.
     *
     * @param aContext
     *      Context for shared preferences.
     * @param aDatabase
     *      Database to inject.
     * @param aUserService
     *      User service.
     */
    public UserRepository(@NonNull Context aContext,
                          @NonNull UsersDatabase aDatabase,
                          @NonNull IRandomUserClient aUserService) {

        iCache = new ArrayList<>();
        iUserDao = aDatabase.getUserDao();
        iNetworkClient = aUserService;
        iSharedPreferences = aContext
                .getSharedPreferences(NETWORK_PREFERENCES, Context.MODE_PRIVATE);

        iPreviousNetworkFetchTime = getPreviousNetworkFetchTime();
    }

    /**
     * @return Stream with persons. If there are no source specified returns previously cached values.
     */
    public Single<List<User>> getPersons(boolean aForceNetwork) {

        return Single
                .defer(() -> {

                    final Flowable<List<User>> memorySource = getPersonsFromCache();
                    final Flowable<List<User>> databaseSource = loadPersonsFromDatabase();
                    final Flowable<List<User>> networkSource = fetchPersonsFromNetwork();

                    if (aForceNetwork) {

                        return networkSource
                                .onErrorResumeNext(memorySource)
                                .onErrorResumeNext(databaseSource)
                                .first(iCache);
                    } else {

                        return memorySource
                                .onErrorResumeNext(databaseSource)
                                .onErrorResumeNext(networkSource)
                                .first(iCache);
                    }

                });
    }

    /**
     * Retrieves user from database.
     *
     * @param aId
     *      Id of user.
     * @return Stream with single user.
     */
    public Single<User> getUser(int aId) {

        return iUserDao.getUser(aId);
    }

    /**
     * @return Stream with data from database.
     */
    @NonNull
    private Flowable<List<User>> loadPersonsFromDatabase() {

        return Flowable.defer(() -> iUserDao
                    .getUsers()
                    .doOnSuccess(iCache::addAll)
                    .toFlowable());
    }

    /**
     * @return Stream with data from network service.
     */
    @NonNull
    private Flowable<List<User>> fetchPersonsFromNetwork() {

        return iNetworkClient.getUsers()
                .doOnNext(users -> iUserDao.deleteAll())
                .doOnNext(iUserDao::saveUsers)
                .doOnNext(iCache::addAll)
                .doOnNext(data -> setPreviousNetworkFetchTime(new Instant()));
    }

    /**
     * @return Stream with persons data from cache.
     */
    @NonNull
    private Flowable<List<User>> getPersonsFromCache() {

        return Flowable.defer(() -> {

            if (iCache.isEmpty()
                    || isDataStale()) {

                iCache.clear();
                return Flowable.error(new NoSuchElementException("Data is stale"));
            } else {

                return Flowable.just(iCache);
            }
        });
    }

    /**
     * Checks is data stale or not.
     *
     * @return true if data stale, otherwise returns false.
     */
    private boolean isDataStale() {

        if (iPreviousNetworkFetchTime == null) {

            return true;
        } else {

            final Instant now = new Instant();

            final Interval networkFetchInterval = new Interval(iPreviousNetworkFetchTime, now);

            return MAXIMUM_STALE_DATA_INTERVAL.isShorterThan(networkFetchInterval.toDuration());
        }
    }

    /**
     * @return Previous network fetch time.
     */
    private Instant getPreviousNetworkFetchTime() {

        return new Instant(iSharedPreferences
                .getLong(PREVIOUS_NETWORK_FETCH_KEY, 0));
    }

    /**
     * @param aPreviousNetworkFetchTime
     *      Sets previous network fetch time.
     */
    private void setPreviousNetworkFetchTime(Instant aPreviousNetworkFetchTime) {

        iSharedPreferences.edit()
                .putLong(PREVIOUS_NETWORK_FETCH_KEY,
                        aPreviousNetworkFetchTime.getMillis())
                .apply();
    }
}
