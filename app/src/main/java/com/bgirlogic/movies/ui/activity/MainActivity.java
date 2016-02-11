package com.bgirlogic.movies.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.movie.Movie;
import com.bgirlogic.movies.ui.fragment.DetailedFragment;
import com.bgirlogic.movies.ui.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private View mMainCoordinatorLayoutView;

    private boolean mTwoPane = false;

    private Movie mMovie;

    public static Intent newIntentForDetailedFragment(Context context, Movie movie) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(DetailedActivity.PARAM_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMainCoordinatorLayoutView = findViewById(R.id.activity_base);

        ((App) getApplication()).getDaggerComponent().inject(this);

        if (findViewById(R.id.container2) != null) {
            mTwoPane = true;
            if (savedInstanceState != null) {
                return;
            }
            inflateMainFragment();

            mMovie = getIntent().getParcelableExtra(DetailedActivity.PARAM_MOVIE);
            inflateDetailFragment(mMovie);

        } else {
            inflateMainFragment();
        }
    }

    protected int getContainerId() {
        return R.id.container;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void inflateMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(getContainerId(), MainFragment.newInstance())
                .commit();
    }

    private void inflateDetailFragment(Movie movie) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container2, DetailedFragment.newInstance(movie))
                .commit();
    }
}
