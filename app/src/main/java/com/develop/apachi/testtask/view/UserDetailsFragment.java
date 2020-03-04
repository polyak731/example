package com.develop.apachi.testtask.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.develop.apachi.testtask.R;
import com.develop.apachi.testtask.glide_app.GlideApp;
import com.develop.apachi.testtask.model.User;
import com.develop.apachi.testtask.utils.DateUtils;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

/**
 * Screen with user details. It's better to use DataBindings, but butterknife is already setup.
 */
public class UserDetailsFragment extends BaseFragment {

    private static final String USER_ARG_KEY = "user_arg";

    @BindView(R.id.person_image)
    protected ImageView iUserImage;

    @BindView(R.id.full_user_name_text)
    protected TextView iPersonFullNameText;

    @BindView(R.id.user_name_text)
    protected TextView iPersonUserNameText;

    @BindView(R.id.location_text)
    protected TextView iLocationText;

    @BindView(R.id.post_code_text)
    protected TextView iPostCodeText;

    @BindView(R.id.email_text)
    protected TextView iEmailText;

    @BindView(R.id.date_of_birth_text)
    protected TextView iDateOfBirthText;

    /** User to display on fragment.*/
    @Nullable
    private User iUser;

    /** User subscription.*/
    private Disposable iUserSubscription = Disposables.empty();

    /**
     * Creates new instance of fragment.
     *
     * @param aUserId
     *      User id.
     * @return Instance of user details fragment.
     */
    public static UserDetailsFragment newInstance(int aUserId) {

        final Bundle args = new Bundle();

        UserDetailsFragment fragment = new UserDetailsFragment();
        fragment.setArguments(args);

        args.putInt(USER_ARG_KEY, aUserId);

        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater aInflater,
                             @Nullable ViewGroup aContainer,
                             @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_person_details, aContainer, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(@NonNull View aView, @Nullable Bundle aSavedInstanceState) {
        super.onViewCreated(aView, aSavedInstanceState);

        loadArgs(getArguments());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle aSavedInstanceState) {
        super.onViewStateRestored(aSavedInstanceState);

        loadArgs(aSavedInstanceState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle aOutState) {
        super.onSaveInstanceState(aOutState);

        saveArgs(aOutState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyView() {

        iUserSubscription.dispose();

        super.onDestroyView();
    }

    /**
     * Loads args from saved state or arguments.
     */
    private void loadArgs(@Nullable Bundle aArgs) {

        if (aArgs != null && aArgs.containsKey(USER_ARG_KEY)) {

            final int userId = aArgs.getInt(USER_ARG_KEY);

            iUserSubscription = iRandomUserViewModel
                    .getUser(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayUser, this::handleError);
        }
    }

    /**
     * Handles error returned from Room.
     *
     * @param aThrowable
     *      Exception.
     */
    private void handleError(Throwable aThrowable) {

        Toast.makeText(requireContext(), R.string.user_details_no_user_error, Toast.LENGTH_LONG)
                .show();
    }

    /**
     * Saves args to bundle.
     *
     * @param aArgs
     *      Arguments or state..
     */
    private void saveArgs(@NonNull Bundle aArgs) {

        if (iUser != null) {
            aArgs.putInt(USER_ARG_KEY, iUser.getId());
        }
    }

    /**
     * Displays user on screen.
     *
     * @param aUser
     *      User.
     */
    private void displayUser(@Nullable User aUser) {

        if (aUser == null) {

            throw new IllegalStateException("User can't be null");
        } else {

            iUser = aUser;
        }

        iDateOfBirthText.setText(getResources()
                .getQuantityString(R.plurals.user_details_age_format,
                        aUser.getAge(),
                        DateUtils.fromDateTime(aUser.getBirthDate()),
                        aUser.getAge()));

        iEmailText.setText(aUser.getEmail());
        Linkify.addLinks(iEmailText, Linkify.EMAIL_ADDRESSES);

        iLocationText.setText(getString(R.string.user_details_location_format,
                aUser.getStreet(),
                aUser.getState(),
                aUser.getCity()));

        iPersonFullNameText.setText(getString(R.string.user_details_full_name_format,
                aUser.getTitle(),
                aUser.getLastName(),
                aUser.getFirstName()));

        iPersonUserNameText.setText(aUser.getUserName());

        GlideApp.with(this)
                .load(aUser.getLargePictureUrl())
                .placeholder(R.mipmap.ic_launcher)
                .apply(RequestOptions.circleCropTransform())
                .into(iUserImage);
    }
}
