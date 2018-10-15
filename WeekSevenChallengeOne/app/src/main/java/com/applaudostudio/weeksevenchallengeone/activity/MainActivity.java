package com.applaudostudio.weeksevenchallengeone.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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


    /****
     * Load de grid fragment and the toolbar
     * @param savedInstanceState dunble on save state
     */
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

    /***
     * Fnction to request permisions
     * @param requestCode code for "0" for the permision of files
     * @param permissions array of permissions
     * @param grantResults result granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mTransaction.commit();
                    //Permission Granted Successfully.
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
        //If the action bar is hide on the detail view this will hide it
        if (getSupportActionBar() != null)
            getSupportActionBar().show();
    }

    /***
     * Listener for the click on the grid
     * @param dataSet data set downloader from the api
     * @param position position selected on the grid
     */
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

    /***
     * callback for de detail simple click
     * @param status boolean status for the bar
     */
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

    /***
     * check the permission to write files for cache
     * @return return true or false
     */
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
