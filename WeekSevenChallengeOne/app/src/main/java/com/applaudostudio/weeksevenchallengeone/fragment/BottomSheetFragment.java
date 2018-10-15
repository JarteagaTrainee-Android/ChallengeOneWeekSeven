package com.applaudostudio.weeksevenchallengeone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applaudostudio.weeksevenchallengeone.R;
import com.applaudostudio.weeksevenchallengeone.model.gsonmodels.Photos;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private static final String ARG_DATA = "PHOTOS_DATA";
    @BindView(R.id.textViewTitleCam)
    TextView txtTitle;
    @BindView(R.id.textViewLandingDate)
    TextView txtLanding;
    @BindView(R.id.textViewLaunchDate)
    TextView txtLaunch;
    @BindView(R.id.textShare)
    TextView txtShare;

    Photos mItem;

    /***
     * instace of the fragment
     * @param item with the data
     * @return returns a fragment instacne
     */
    static BottomSheetFragment newInstance(Photos item) {
        BottomSheetFragment sheetFragment = new BottomSheetFragment();
        Bundle argBundle = new Bundle();
        argBundle.putParcelable(ARG_DATA, item);
        sheetFragment.setArguments(argBundle);
        return sheetFragment;

    }

    /***
     * Create to get the data on on thte params
     * @param savedInstanceState bundle saved
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItem = getArguments().getParcelable(ARG_DATA);
        }
    }

    /***
     * Inflate the view and init the butter
     * @param inflater the view inflater
     * @param container view group container
     * @param savedInstanceState saved dunble
     * @return returns the view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_item, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    /***
     * set the data on the correct place
     * @param savedInstanceState bundle saved
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindData();
    }


    /***
     * click to share the image
     *
     */
    @OnClick(R.id.textShare)
    void shareItem(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                mItem.getImg_src());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }

    /**
     * load view data
     */
    private void bindData(){
        txtTitle.setText(mItem.getCamera().getName());
        txtLanding.setText(mItem.getRover().getLanding_date());
        txtLaunch.setText(mItem.getRover().getLaunch_date());
    }
}
