package com.develop.apachi.testtask.view_model;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.develop.apachi.testtask.model.User;
import com.develop.apachi.testtask.model.UserRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

/**
 * View model for storing the data.
 */
public class RandomUserViewModel extends ViewModel {

    /** Repository with users.*/
    @NonNull
    private final UserRepository iUserRepository;
    /** Defensive disposable.*/
    @NonNull
    private final CompositeDisposable iCompositeDisposable;

    /**
     * @param aUserRepository
     *          User repository.
     */
    public RandomUserViewModel(@NonNull UserRepository aUserRepository) {

        iUserRepository = aUserRepository;
        iCompositeDisposable = new CompositeDisposable();
    }

    /**
     * Retrieves reactive stream with list of users.
     *
     * @param aForceNetwork
     *      True to force network otherwise false.
     * @return
     *      Stream with users.
     */
    @NonNull
    public Single<List<User>> getUsers(boolean aForceNetwork) {

        return iUserRepository
                .getPersons(aForceNetwork)
                .doOnSubscribe(iCompositeDisposable::add); // Def hint ;)
    }

    /**
     * Retrieves user from database.
     *
     * @param aId
     *      Id.
     * @return User from database.
     */
    @NonNull
    public Single<User> getUser(int aId) {

        return iUserRepository
                .getUser(aId)
                .doOnSubscribe(iCompositeDisposable::add);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCleared() {

        iCompositeDisposable.dispose();
        iCompositeDisposable.clear();

        super.onCleared();
    }
}
