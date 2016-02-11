package com.bgirlogic.movies.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.movie.Movie;
import com.bgirlogic.movies.ui.fragment.DetailedFragment;
import butterknife.ButterKnife;

/**
 * Created by Senpai on 1/3/16.
 */
public class DetailedActivity extends AppCompatActivity {

    public static final String PARAM_MOVIE = "movie";

    private Movie mMovie;
    private View mMainCoordinatorLayoutView;

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailedActivity.class);
        intent.putExtra(PARAM_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inject dependencies
        setContentView(R.layout.activity_detail);
        ((App) getApplication()).getDaggerComponent().inject(this);
        mMainCoordinatorLayoutView = findViewById(R.id.container2);

        //inject views
        ButterKnife.bind(this);
        mMovie = getIntent().getParcelableExtra(PARAM_MOVIE);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container2, DetailedFragment.newInstance(mMovie))
                .commit();
    }

//    @Override
//    protected Fragment getFragment() {
//
//    }
}
