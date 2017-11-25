package com.example.adewan.abtestingproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import core.BaseActivity;
import timber.log.Timber;
import viewinjector.ViewInjector;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.section_1)
    FrameLayout mSection1Layout;

    ViewInjector mViewInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.tag(TAG).e("Timber has been initialized");
        ButterKnife.bind(this);
        mViewInjector = ViewInjector.getInstance(this);
        initABViews();
    }

    private void initABViews() {
        // Create a ABView
        View tempView = mViewInjector.createViewFromJson();
        mSection1Layout.addView(tempView);
    }
}
