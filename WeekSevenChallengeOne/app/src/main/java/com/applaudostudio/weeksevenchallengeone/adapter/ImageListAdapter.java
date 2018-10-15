package com.applaudostudio.weeksevenchallengeone.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.applaudostudio.weeksevenchallengeone.R;
import com.applaudostudio.weeksevenchallengeone.model.gsonmodels.Photos;
import com.applaudostudio.weeksevenchallengeone.util.GlideApp;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/***
 * Adapter for the recycler view
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder> {
    public static final int VIEW_TYPE_FULL = 2;
    public static final int VIEW_TYPE_GRID = 1;

    private List<Photos> mDataSet;
    private ItemSelectedSimpleListener mCallback;
    private int mViewType;

    //Adapter for the gallery and the grid
    public ImageListAdapter(List<Photos> mDataSet, ItemSelectedSimpleListener callback, int viewType) {
        this.mDataSet = mDataSet;
        mCallback = callback;
        mViewType = viewType;
    }

    //Function to set the data and notify the changes
    public void setData(List<Photos> mDataSet) {
        this.mDataSet = mDataSet;
        this.notifyDataSetChanged();
    }

    /***
     * Constructor for the view Holder of the recycler view
     * @param parent the parent viewGroup
     * @param viewType Type of view to be render
     * @return returns a RadioViewHolder
     */
    @NonNull
    @Override
    public ImageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_GRID) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_list, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_fullview, parent, false);
        }
        return new ImageListViewHolder(view);
    }

    /***
     * Function to bind the data
     * @param imageListViewHolder viewholder for images
     * @param i position
     */
    @Override
    public void onBindViewHolder(@NonNull ImageListViewHolder imageListViewHolder, int i) {
        imageListViewHolder.bindData(mDataSet.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /***
     * Function to get the item
     * @param position position of the adapter
     * @return returns 0 or 1
     */
    @Override
    public int getItemViewType(int position) {
        if (mViewType == VIEW_TYPE_GRID) {
            return VIEW_TYPE_GRID;
        } else {
            return VIEW_TYPE_FULL;
        }
    }

    /***
     * Class for the news View holder
     */
    class ImageListViewHolder extends ViewHolder {
        @BindView(R.id.imageView)
        ImageView mImageElement;
        @BindView(R.id.imageLayoutContainer)
        ConstraintLayout container;
        @BindView(R.id.progressBarLoadImg)
        ProgressBar progressBar;

        ImageListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.imageLayoutContainer)
        public void clickView() {
            mCallback.onSimpleClickImageDetailClick(getAdapterPosition());
        }

        @OnLongClick(R.id.imageLayoutContainer)
        public boolean longClickView() {
            if (mCallback instanceof ItemSelectorLongListener) {
                ((ItemSelectorLongListener) mCallback).onLongClickImageDetailClick(getAdapterPosition());
            }
            return true;
        }


        /***
         * Function to bind the data to the correct view element
         * @param item item to bind the data
         */
        void bindData(Photos item) {
            GlideApp.with(container)
                    .load(item.getImg_src())
                    .placeholder(R.drawable.placeholder_nasa)
                    .fitCenter()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(mImageElement);
        }
    }

    /***
     * Interface for the click on the items to send the data to the activity
     */
    public interface ItemSelectedSimpleListener {
        void onSimpleClickImageDetailClick(int position);
    }

    public interface ItemSelectorLongListener extends ItemSelectedSimpleListener {
        void onLongClickImageDetailClick(int position);
    }
}



