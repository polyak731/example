package com.develop.apachi.testtask.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.develop.apachi.testtask.R;
import com.develop.apachi.testtask.adapters.UsersAdapter;
import com.develop.apachi.testtask.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

/**
 * Fragment for displaying list of persons.
 *
 * @author Maksym Poliakov
 */
public class PersonListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout iSwipeRefreshLayout;

    @BindView(R.id.users_list)
    protected RecyclerView iUsersList;

    private UsersAdapter iUsersAdapter;

    private Disposable iUsersSubscription = Disposables.empty();

    @Nullable
    private OnUserSelectedListener iUserSelectedListener;

    /**
     * Creates new instance of fragment.
     *
     * @return
     *      new instance of fragment.
     */
    public static PersonListFragment newInstance() {

        return new PersonListFragment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context aContext) {
        super.onAttach(aContext);

        if (aContext instanceof OnUserSelectedListener) {

            iUserSelectedListener = (OnUserSelectedListener) aContext;
        } else {

            throw new IllegalArgumentException(String.format("%s must implement %s",
                    aContext,
                    OnUserSelectedListener.class));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater aInflater,
                             @Nullable ViewGroup aContainer,
                             @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_person_list, aContainer, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(@NonNull View aView,
                              @Nullable Bundle aSavedInstanceState) {
        super.onViewCreated(aView, aSavedInstanceState);

        setupView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyView() {

        disposeAllSubscriptions();

        super.onDestroyView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {

        iUserSelectedListener = null;
        iRandomUserViewModel = null;

        super.onDetach();
    }

    /**
     * Setups view.
     */
    private void setupView() {

        iUsersList.addItemDecoration(
                new DividerItemDecoration(requireContext(),
                        DividerItemDecoration.VERTICAL));

        iUsersAdapter = prepareAdapter(iUsersList);

        iUsersAdapter.setOnItemClickListener(user -> {

            if (iUserSelectedListener != null) {

                iUserSelectedListener.onUserSelected(user);
            }
        });

        iUsersList.setAdapter(iUsersAdapter);

        iSwipeRefreshLayout.setOnRefreshListener(this);

        requestAllUsers(false);
    }

    /**
     * Requests all the users from current service.
     *
     * @param aForceNetwork
     *      True to force network otherwise false.
     */
    private void requestAllUsers(boolean aForceNetwork) {

        iUsersSubscription = iRandomUserViewModel.getUsers(aForceNetwork)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> displayLoading())
                .doOnSuccess(result -> hideLoading())
                .doOnError(throwable -> hideLoading())
                .subscribe(iUsersAdapter::updateData, this::handleException);
    }

    /**
     * Disposes all the subscriptions.
     */
    private void disposeAllSubscriptions() {
        iUsersSubscription.dispose();
    }

    /**
     * Prepares adapter for users.
     *
     * @return Adapter.
     */
    private UsersAdapter prepareAdapter(@NonNull RecyclerView aRecyclerView) {

        return new UsersAdapter(new ArrayList<>(), aRecyclerView);
    }

    /**
     * Handles exception.
     *
     * @param aException
     *      Exception.
     */
    private void handleException(@NonNull Throwable aException) {

        Toast.makeText(requireContext(), aException.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * Displays loading.
     */
    private void displayLoading() {

        iSwipeRefreshLayout.setRefreshing(true);
    }

    /**
     * Hides loading.
     */
    private void hideLoading() {

        iSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRefresh() {

        requestAllUsers(true);
    }

    /** Callback for users.*/
    public interface OnUserSelectedListener {

        /**
         * Calls when user is selected.
         *
         * @param aUser
         *      User.
         */
        void onUserSelected(User aUser);
    }
}
