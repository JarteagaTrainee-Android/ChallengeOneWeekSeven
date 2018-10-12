package com.applaudostudio.weeksevenchallengeone.fragment;

import android.content.Intent;
import android.os.Bundle;
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
    private ShareActionProvider mShareActionProvider;
    Photos mItem;

    static BottomSheetFragment newInstance(Photos item) {
        BottomSheetFragment sheetFragment = new BottomSheetFragment();
        Bundle argBundle = new Bundle();
        argBundle.putParcelable(ARG_DATA, item);
        sheetFragment.setArguments(argBundle);
        return sheetFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItem = getArguments().getParcelable(ARG_DATA);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_item, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtTitle.setText(mItem.getCamera().getName());
        txtLanding.setText(mItem.getRover().getLanding_date());
        txtLaunch.setText(mItem.getRover().getLaunch_date());
    }


    @OnClick(R.id.textShare)
    void shareItem(View view){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check this NASA IMAGE at: "+mItem.getImg_src());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }
}
