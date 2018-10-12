package com.applaudostudio.weeksevenchallengeone.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewOutlineProvider;

import com.applaudostudio.weeksevenchallengeone.R;
import com.applaudostudio.weeksevenchallengeone.fragment.DetailFragment;
import com.applaudostudio.weeksevenchallengeone.fragment.ImageListFragment;
import com.applaudostudio.weeksevenchallengeone.model.gsonmodels.Photos;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ImageListFragment.GridElementListener, DetailFragment.ToolbarStatusListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ViewOutlineProvider mOutlineProvider;
    private FragmentTransaction mTransaction;
    private final static int PERMISSION_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = ImageListFragment.newInstance();
        mTransaction = manager.beginTransaction();
        mTransaction.replace(R.id.containerList, fragment);
        if (mOutlineProvider != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary, this.getTheme()));
            toolbar.setOutlineProvider(mOutlineProvider);
        }
        setSupportActionBar(toolbar);
        if (checkAndRequestPermissions()) {
            mTransaction.commit();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mTransaction.commit();
                    //Permission Granted Successfully. Write working code here.
                } else {
                    //You did not accept the request can not use the functionality.
                    checkAndRequestPermissions();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getSupportActionBar() != null)
            getSupportActionBar().show();
    }

    @Override
    public void onClickGridElement(List<Photos> dataSet, int position) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = DetailFragment.newInstance(dataSet, position);
        FragmentTransaction transactions = manager.beginTransaction()
                .add(R.id.fullScreenContainer, fragment, "detail")
                .addToBackStack(null);
        transactions.commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            mOutlineProvider = toolbar.getOutlineProvider();
            toolbar.setOutlineProvider(null);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary, this.getTheme()));
            toolbar.bringToFront();
        }
    }

    @Override
    public void viewClickListener(boolean status) {
        if (getSupportActionBar() != null) {
            if (status) {
                getSupportActionBar().hide();
            } else {
                getSupportActionBar().show();
            }
        }
    }


    private boolean checkAndRequestPermissions() {

        //Here i am checking for account Permission
        int accountPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (accountPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);

            return false;
        }

        return true;
    }


}
