package com.develop.apachi.testtask.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.develop.apachi.testtask.model.User;
import com.develop.apachi.testtask.model.adapters.UserDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Random users service client.
 */
public class RandomUsersClientImpl implements IRandomUserClient {

    private static final int MAX_USER_COUNT = 100;
    private static final int MAX_USER_PAGE_COUNT = 10;

    /** Instance of user service.*/
    private IUserService iUserService;

    /**
     * Creates new instance
     *
     * @param iServiceUrl
     *      Service url.
     */
    public RandomUsersClientImpl(@NonNull String iServiceUrl) {

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<User>(){}.getType(), new UserDeserializer())
                .setLenient()
                .create();

        iUserService = new Retrofit.Builder()
                .baseUrl(iServiceUrl)
                .client(configureClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(IUserService.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Flowable<List<User>> getUsers() {
        return iUserService.getUsers(MAX_USER_COUNT)
                .map(response -> response.results);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Flowable<List<User>> getUsers(int aPageNumber) {
        return iUserService.getUsers(MAX_USER_PAGE_COUNT, aPageNumber)
                .map(response -> response.results);
    }

    /**
     * @return configured {@link OkHttpClient} instance.
     */
    private OkHttpClient configureClient() {

        return new OkHttpClient.Builder()
                .build();
    }
}
