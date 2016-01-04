package com.bgirlogic.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.Movie;
import com.bgirlogic.movies.common.Utils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Senpai on 1/3/16.
 */
public class DetailedActivity extends AppCompatActivity {

    public static final String PARAM_MOVIE = "movie";

    @Bind(R.id.image)
    protected ImageView mImageView;

    @Bind(R.id.title)
    protected TextView mTitle;

    @Bind(R.id.release_date)
    protected TextView mReleaseDate;

    @Bind(R.id.vote_average)
    protected TextView mVoteAverage;

    @Bind(R.id.overview)
    protected TextView mOverview;

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

        setContentView(R.layout.activity_detailed);

        //inject views
        ButterKnife.bind(this);

        mMovie = getIntent().getParcelableExtra(PARAM_MOVIE);

        Picasso.with(this)
                .load(Utils.getImageUrl(mMovie.getPosterPath())).into(mImageView);

        mTitle.setText("Title: " + mMovie.getTitle());

        mReleaseDate.setText("Release date: " + mMovie.getReleaseDate());

        mVoteAverage.setText("Average vote: " + Math.round(mMovie.getmVoteAverage()));

        mOverview.setText("Plot Synopsis: " + mMovie.getOverview());
    }
}
