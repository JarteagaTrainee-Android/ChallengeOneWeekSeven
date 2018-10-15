package com.applaudostudio.weeksevenchallengeone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applaudostudio.weeksevenchallengeone.R;
import com.applaudostudio.weeksevenchallengeone.adapter.ImageListAdapter;
import com.applaudostudio.weeksevenchallengeone.model.gsonmodels.Photos;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class    DetailFragment extends Fragment implements ImageListAdapter.ItemSelectorLongListener {
    private static final String KEY_ARG_DATASET = "DATA_SET";
    private static final String KEY_ARG_POSITION = "DATA_POSITION";
    private ToolbarStatusListener mCallBack;
    private List<Photos> mDataSet;
    private boolean mToolBarStatus;
    private int mPosition;
    @BindView(R.id.recyclerViewFullView)
    RecyclerView mRecyclerViewFullView;

    public DetailFragment() {
        // Required empty public constructor
    }

    /***
     * Function to generate instances of detail fragment
     * @param mDataSet data set
     * @param position position for init load
     * @return returns the a instance of the fragment
     */
    public static DetailFragment newInstance(List<Photos> mDataSet, int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_ARG_DATASET, (ArrayList<? extends Parcelable>) mDataSet);
        args.putInt(KEY_ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    /***
     * get the arguments of the fragment
     * @param savedInstanceState bundle for save instance
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDataSet = getArguments().getParcelableArrayList(KEY_ARG_DATASET);
            mPosition = getArguments().getInt(KEY_ARG_POSITION);

        }
    }

    /***
     * function to inflate the view and init the butterknife
     * @param inflater xml inflater
     * @param container view group container
     * @param savedInstanceState bundle for saved instance
     * @return returns a inflated view.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, v);
        mToolBarStatus = false;
        return v;
    }

    /***
     * on destoy notify the activity to show the toolbar
     */
    @Override
    public void onDestroy() {
        mCallBack.viewClickListener(false);
        super.onDestroy();
    }

    /***
     * funtion to init the adapter an set the data for the gallery view.
     * @param savedInstanceState bundle for saveinstance
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageListAdapter mImageListAdapter = new ImageListAdapter(mDataSet, this, ImageListAdapter.VIEW_TYPE_FULL);
        mRecyclerViewFullView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewFullView.setAdapter(mImageListAdapter);
        mRecyclerViewFullView.scrollToPosition(mPosition);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerViewFullView);


    }

    /***
     * get the context for the callback
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        mCallBack = (ToolbarStatusListener) context;
        super.onAttach(context);
    }

    /***
     * simple click to show and hide the toolbar
     * @param position position of the adapter
     */
    @Override
    public void onSimpleClickImageDetailClick(int position) {
        mCallBack.viewClickListener(mToolBarStatus);
        if (mToolBarStatus) {
            mToolBarStatus = false;
        } else {
            mToolBarStatus = true;
        }
    }

    /***
     * long click to show the bottom sheet
     * @param position position clicked
     */
    @Override
    public void onLongClickImageDetailClick(int position) {
        BottomSheetFragment bsdFragment = BottomSheetFragment.newInstance(mDataSet.get(position));
        bsdFragment.show(getFragmentManager(), getString(R.string.tag_bottomSheet));
    }

    /***
     * listener for the toolbar status
     */
    public interface ToolbarStatusListener {
        void viewClickListener(boolean status);
    }

}
