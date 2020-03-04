package com.develop.apachi.testtask.network;

import com.develop.apachi.testtask.model.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Random user client contract.
 */
public interface IRandomUserClient {

    /**
     * @return Reactive streams with users.
     */
    Flowable<List<User>> getUsers();

    /**
     * Retrieves users from service
     *
     * @param aPageNumber
     *      Page number.
     * @return Reactive streams with users.
     */
    Flowable<List<User>> getUsers(int aPageNumber);
}
