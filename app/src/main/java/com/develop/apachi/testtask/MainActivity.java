package com.develop.apachi.testtask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.develop.apachi.testtask.model.User;
import com.develop.apachi.testtask.model.UserRepository;
import com.develop.apachi.testtask.view.PersonListFragment;
import com.develop.apachi.testtask.view.UserDetailsFragment;

/**
 * Landscape activity host for person list fragment and person details fragment.
 */
public class MainActivity extends AppCompatActivity implements IRepositoryProvider,
        PersonListFragment.OnUserSelectedListener {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_main);

        displayListFragment();
    }

    /**
     * Displays list fragment.
     */
    public void displayListFragment() {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.first_container, PersonListFragment.newInstance())
                .commit();
    }

    /**
     * Displays details fragment.
     */
    public void displayDetailsFragment(@NonNull User aUser) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.second_container, UserDetailsFragment.newInstance(aUser.getId()))
                .commit();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public UserRepository getCurrentRepository() {
        return ((MainApplication) getApplication()).getUserRepository();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUserSelected(User aUser) {

        displayDetailsFragment(aUser);
    }
}
