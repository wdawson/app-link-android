package com.okta.browserexperiment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends Activity implements CctFragment.OnCctFragmentInteractionListener {

    private static final String TAG = "BROWSER_EXP";

    private enum Navigation {
        WV_OVERRIDE,
        WV,
        CCT
    }

    private Navigation mNavigation;

    private FragmentManager mFragmentManager;

    private Fragment mWvOverrideFragment;
    private Fragment mWvFragment;
    private Fragment mCctFragment;

    private Fragment mCurrentFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wv_override:
                    Log.d(TAG, "WV Override");
                    mNavigation = Navigation.WV_OVERRIDE;
                    switchNavigation();
                    return true;
                case R.id.navigation_wv:
                    Log.d(TAG, "WV");
                    mNavigation = Navigation.WV;
                    switchNavigation();
                    return true;
                case R.id.navigation_cct:
                    Log.d(TAG, "CCT");
                    mNavigation = Navigation.CCT;
                    switchNavigation();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFragmentManager = getFragmentManager();
        mWvOverrideFragment = mFragmentManager.findFragmentById(R.id.wv_override_fragment);
        mWvFragment = mFragmentManager.findFragmentById(R.id.wv_fragment);
        mCctFragment = mFragmentManager.findFragmentById(R.id.cct_fragment);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(mWvFragment);
        transaction.hide(mCctFragment);
        transaction.show(mWvOverrideFragment);
        transaction.commit();

        mCurrentFragment = mWvOverrideFragment;
        mNavigation = Navigation.WV_OVERRIDE;
    }

    private void switchNavigation() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(mCurrentFragment);
        switch (mNavigation) {
            case WV_OVERRIDE:
                mCurrentFragment = mWvOverrideFragment;
                break;
            case WV:
                mCurrentFragment = mWvFragment;
                break;
            case CCT:
                mCurrentFragment = mCctFragment;
                break;
        }
        transaction.show(mCurrentFragment);
        transaction.commit();
    }

    @Override
    public void onLaunchButtonClicked() {
        // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
        String url = getString(R.string.initial_site_url);
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        // set toolbar color and/or setting custom actions before invoking build()
        // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        CustomTabsIntent customTabsIntent = builder.build();
        // and launch the desired Url with CustomTabsIntent.launchUrl()
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
