package com.example.remoteviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.RelativeLayout;

import com.example.remoteviews.abtesting.network.ABRemoteClient;


public class MainActivity extends AppCompatActivity implements ABRemoteClient.ABExperimentLoadedInterface {

    private boolean mIsExperimentRunning = true;
    RelativeLayout mRelativeLayout;
    ABRemoteClient mRemoteClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = findViewById(R.id.root);
        mRemoteClient = ABRemoteClient.getInstance();
        mRemoteClient.init(this, this);
    }

    @Override
    public void experiementHasLoaded() {
        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(mRemoteClient.getExperimentView(getApplicationContext()));
    }
}
