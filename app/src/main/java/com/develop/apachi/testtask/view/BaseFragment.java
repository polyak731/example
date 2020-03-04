package com.develop.apachi.testtask.view;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.develop.apachi.testtask.IRepositoryProvider;
import com.develop.apachi.testtask.model.UserRepository;
import com.develop.apachi.testtask.view_model.RandomUserViewModel;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Base fragment with butterknife bindable interface.
 */
public class BaseFragment extends Fragment {

    private Unbinder iUnbinder;

    /** View model with access to data.*/
    @Nullable
    protected RandomUserViewModel iRandomUserViewModel;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context aContext) {
        super.onAttach(aContext);

        if (aContext instanceof IRepositoryProvider) {

            iRandomUserViewModel = attachViewModel(Objects
                    .requireNonNull(((IRepositoryProvider) aContext).getCurrentRepository()));
        } else {

            throw new IllegalArgumentException(String.format("%s must implement %s",
                    aContext,
                    IRepositoryProvider.class));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(@NonNull View aView, @Nullable Bundle aSavedInstanceState) {
        super.onViewCreated(aView, aSavedInstanceState);

        iUnbinder = ButterKnife.bind(this, aView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyView() {

        iUnbinder.unbind();

        super.onDestroyView();
    }

    /**
     * Attaches view model to a view.
     *
     * @param aUserRepository
     *      Model that be attached to the viewModel.
     */
    protected RandomUserViewModel attachViewModel(@NonNull UserRepository aUserRepository) {

        return ViewModelProviders
                .of(this, new ViewModelProvider.Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> aModelClass) {

                        if (aModelClass.isAssignableFrom(RandomUserViewModel.class)) {
                            return (T) new RandomUserViewModel(aUserRepository);
                        } else {
                            throw new IllegalArgumentException(
                                    String.format("No such ViewModel. Please use %s", RandomUserViewModel.class));
                        }
                    }
                })
                .get(RandomUserViewModel.class);
    }
}
