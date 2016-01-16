package com.bgirlogic.movies.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.movie.Movie;
import com.bgirlogic.movies.common.Utils;
import com.bgirlogic.movies.ui.fragment.DetailedFragment;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Senpai on 1/3/16.
 */
public class DetailedActivity extends BaseActivity {

    public static final String PARAM_MOVIE = "movie";

    private Movie mMovie;

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailedActivity.class);
        intent.putExtra(PARAM_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inject dependencies
        ((App) getApplication()).getDaggerComponent().inject(this);

        //inject views
        ButterKnife.bind(this);
    }

    @Override
    protected Fragment getFragment() {
        mMovie = getIntent().getParcelableExtra(PARAM_MOVIE);
        return DetailedFragment.newInstance(mMovie);//null here
    }
}
