package com.applaudostudio.weeksevenchallengeone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applaudostudio.weeksevenchallengeone.R;
import com.applaudostudio.weeksevenchallengeone.adapter.ImageListAdapter;
import com.applaudostudio.weeksevenchallengeone.apiclient.PhotosService;
import com.applaudostudio.weeksevenchallengeone.model.gsonmodels.JsonModel;
import com.applaudostudio.weeksevenchallengeone.model.gsonmodels.Photos;
import com.applaudostudio.weeksevenchallengeone.util.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImageListFragment extends Fragment implements ImageListAdapter.ItemSelectedSimpleListener {
    private PhotosService mPhotoService;
    private List<Photos> mListResult;
    private ImageListAdapter mImageListAdapter;
    private GridElementListener mCallBack;
    private int mPagerCount;
    @BindView(R.id.recyclerViewImages)
    RecyclerView mRecyclerViewImages;


    public ImageListFragment() {
        // Required empty public constructor
    }

    /***
     * Function to generate instances for the fragment
     * @return return instances
     */
    public static ImageListFragment newInstance() {
        ImageListFragment fragment = new ImageListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /***
     * init the data and the pager count
     * @param savedInstanceState saved instance for saved data
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListResult = new ArrayList<>();
        mPagerCount = 1;

    }

    /***
     * function to inflate the view and init the butter knife
     * @param inflater inflater for the xml
     * @param container view group container
     * @param savedInstanceState saved instance
     * @return returns the view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_image_list, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    /***
     * init the data and the adapter for the recycler view
     * @param savedInstanceState saved data for bundle
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPhotoService = ApiUtils.getPhotosService();
        // use a linear layout manager
        mListResult = new ArrayList<>();
        mImageListAdapter = new ImageListAdapter(mListResult, this, ImageListAdapter.VIEW_TYPE_GRID);
        mRecyclerViewImages.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerViewImages.setAdapter(mImageListAdapter);
        getUsersList(mPagerCount);
        mRecyclerViewImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    mPagerCount++;
                    getUsersList(mPagerCount);
                }
            }
        });
    }

    /***
     * callback for the the fragment on the activity
     * @param context context as param
     */
    @Override
    public void onAttach(Context context) {
        mCallBack = (GridElementListener) context;
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    /***
     * function to get the user as list
     * @param page page counter
     */
    public void getUsersList(int page) {
        Call<JsonModel> call = mPhotoService.getPhotos(50, page, ApiUtils.API_KEY);
        call.enqueue(new Callback<JsonModel>() {
            @Override
            public void onResponse(Call<JsonModel> call, Response<JsonModel> response) {
                if(response.body()!=null) {
                    if (response.body() != null) {
                        mListResult.addAll(response.body().getPhotosList());
                    }
                    mImageListAdapter.setData(mListResult);
                    mImageListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonModel> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    /***
     * simple click listener
     * @param position position of the adapter
     */
    @Override
    public void onSimpleClickImageDetailClick(int position) {
        mCallBack.onClickGridElement(mListResult, position);
    }

    /***
     * grid element listener
     */
    public interface GridElementListener {
        void onClickGridElement(List<Photos> dataSet, int position);
    }
}
