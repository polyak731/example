package com.develop.apachi.testtask.adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.FixedPreloadSizeProvider;
import com.develop.apachi.testtask.R;
import com.develop.apachi.testtask.glide_app.GlideApp;
import com.develop.apachi.testtask.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Persons recycler view adapter.
 *
 * @author Maksym Poliakov
 */
public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PRELOAD_IMAGE_WIDTH = 1024;
    private static final int PRELOAD_IMAGE_HEIGHT = 720;

    private static final int PRELOAD_COUNT = 10;

    /** List of persons.*/
    @NonNull
    private final List<User> iUsers;

    /** Listener for user clicks.*/
    @Nullable
    private OnItemClickListener iItemClickListener;

    @NonNull
    private final RecyclerViewPreloader<User> iPreloader;

    /**
     * Persons adapter.
     *
     * @param aUsers
     *      List of persons.
     * @param aRecyclerView
     *      Recycler view.
     */
    public UsersAdapter(@NonNull List<User> aUsers,
                        @NonNull RecyclerView aRecyclerView) {

        iUsers = new ArrayList<>(aUsers);

        final ListPreloader.PreloadSizeProvider<User> sizeProvider =
                new FixedPreloadSizeProvider<>(PRELOAD_IMAGE_WIDTH, PRELOAD_IMAGE_HEIGHT);

        final UserPreloadModelProvider preloadModelProvider = new UserPreloadModelProvider(aUsers,
                new ContextWrapper(aRecyclerView.getContext()));

        iPreloader = new RecyclerViewPreloader<>(Glide.with(aRecyclerView),
                preloadModelProvider, sizeProvider, PRELOAD_COUNT);

        aRecyclerView.addOnScrollListener(iPreloader);
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup aViewGroup, int aPosition) {
        return new UserViewHolder(LayoutInflater.from(aViewGroup.getContext())
                .inflate(R.layout.user_list_item_layout, aViewGroup, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder aViewHolder, int aPosition) {

        final User user = iUsers.get(aPosition);

        final UserViewHolder userViewHolder = (UserViewHolder) aViewHolder;

        setUserFullName(userViewHolder, user);
        setUserName(userViewHolder, user);
        loadImage(userViewHolder, user);

        aViewHolder.itemView.setOnClickListener(view -> handleClick(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return iUsers.size();
    }

    /**
     * Updates data with users.
     *
     * @param aUsers
     *      Users.
     */
    public void updateData(List<User> aUsers) {

        iUsers.clear();
        iUsers.addAll(aUsers);

        notifyDataSetChanged();
    }

    /**
     * Sets click listener on view.
     *
     * @param aClickListener
     *      Click listener.
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener aClickListener) {

        iItemClickListener = aClickListener;
    }

    /**
     * Setups user full name.
     *
     * @param aUserViewHolder
     *      User view holder.
     * @param aUser
     *      User.
     */
    private void setUserFullName(@NonNull UserViewHolder aUserViewHolder, @NonNull User aUser) {

        final String fullUserName = aUserViewHolder.itemView.getResources()
                .getString(R.string.user_list_item_full_name_format,
                        aUser.getTitle(),
                        aUser.getFirstName(),
                        aUser.getLastName());

        aUserViewHolder.iFullName.setText(fullUserName);
    }

    /**
     * Sets user name.
     *
     * @param aUserViewHolder
     *      user view holder.
     * @param aUser
     *      User
     */
    private void setUserName(@NonNull UserViewHolder aUserViewHolder, @NonNull User aUser) {

        aUserViewHolder.iUserName.setText(aUser.getUserName());
    }

    /**
     * Loads image for user.
     *
     * @param aUserViewHolder
     *      User's view holder.
     * @param aUser
     *      User.
     */
    private void loadImage(@NonNull UserViewHolder aUserViewHolder, @NonNull User aUser) {


        GlideApp.with(aUserViewHolder.iImageView)
                .load(aUser.getMediumPictureUrl())
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.mipmap.ic_launcher)
                .into(aUserViewHolder.iImageView);
    }

    /**
     * Handles click on a view.
     *
     * @param aUser
     *      View.
     */
    private void handleClick(@NonNull User aUser) {

        if (iItemClickListener != null) {

            iItemClickListener.onItemClick(aUser);
        }
    }

    /**
     * User view holder.
     */
    /*package*/ static class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.full_name_text)
        /*package*/ TextView iFullName;
        @BindView(R.id.user_name_text)
        /*package*/ TextView iUserName;
        @BindView(R.id.user_image)
        /*package*/ ImageView iImageView;

        /**
         * Constructor.
         *
         * @param aItemView
         *      Item view.
         */
        /*package*/ UserViewHolder(@NonNull View aItemView) {
            super(aItemView);

            ButterKnife.bind(this, aItemView);
        }
    }

    /** Callback on user clicks on item.*/
    public interface OnItemClickListener {

        /**
         * Calls when user clicks on item.
         *
         * @param aUser
         *      User.
         */
        void onItemClick(User aUser);
    }

    /**
     * Preloads users images on scrolling.
     */
    private static class UserPreloadModelProvider implements ListPreloader.PreloadModelProvider<User> {

        /** List of users to preload.*/
        @NonNull
        private List<User> iUsers;
        /** Context for glide.*/
        @NonNull
        private Context iContext;

        /**
         * @param aUsers
         *      List of users.
         * @param aContext
         *      Any context.
         */
        private UserPreloadModelProvider(@NonNull List<User> aUsers, @NonNull Context aContext) {

            iUsers = aUsers;
            iContext = aContext;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NonNull
        public List<User> getPreloadItems(int aPosition) {

            if (aPosition < iUsers.size()) {

                if (TextUtils.isEmpty(iUsers.get(aPosition).getMediumPictureUrl())) {
                    return Collections.emptyList();
                }

                return Collections.singletonList(iUsers.get(aPosition));
            }

            return Collections.emptyList();
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public RequestBuilder<?> getPreloadRequestBuilder(@NonNull User aUser) {
            return Glide.with(iContext)
                    .load(aUser.getMediumPictureUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .override(PRELOAD_IMAGE_WIDTH, PRELOAD_IMAGE_HEIGHT);
        }
    }
}
