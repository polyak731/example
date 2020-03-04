package com.develop.apachi.testtask;

import android.support.annotation.Nullable;

import com.develop.apachi.testtask.model.UserRepository;

/**
 * Provider for repository instance.
 */
public interface IRepositoryProvider {

    /**
     * Retrieves current user repository.
     *
     * @return User repository instance.
     */
    @Nullable
    UserRepository getCurrentRepository();
}
