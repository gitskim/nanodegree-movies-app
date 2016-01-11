package com.bgirlogic.movies.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;

/**
 * Created by Senpai on 1/10/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName();

    private View mMainCoordinatorLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMainCoordinatorLayoutView = findViewById(R.id.activity_base);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(getContainerId(), getFragment())
                    .commit();
        }
    }

    protected int getContainerId() {
        return R.id.container;
    };

    protected abstract Fragment getFragment();
}
