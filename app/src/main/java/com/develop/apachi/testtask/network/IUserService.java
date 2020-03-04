package com.develop.apachi.testtask.network;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Service for retrieving persons.
 */
public interface IUserService {

    /**
     * Retrieves users bounded by count.
     *
     * @param aMaxCount
     *      Maximum count of users.
     * @return Reactive stream with users.
     */
    @GET("api/")
    Flowable<GeneralResponse> getUsers(@Query("results") int aMaxCount);

    /**
     * Retrieves list of users on page bounded by maximum count.
     *
     * @param aMaxCount
     *      Max count.
     * @param aPage
     *      Page.
     * @return Reactive stream with users.
     */
    @GET("api/")
    Flowable<GeneralResponse> getUsers(@Query("results") int aMaxCount, @Query("page") int aPage);
}
