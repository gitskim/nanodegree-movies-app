package com.bgirlogic.movies.ui.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.movie.Movie;
import com.bgirlogic.movies.api.models.review.Review;
import com.bgirlogic.movies.api.models.trailer.Trailer;
import com.bgirlogic.movies.common.Utils;
import com.bgirlogic.movies.data.MoviesContract;
import com.bgirlogic.movies.ui.presenter.DetailedPresenterImp;
import com.bgirlogic.movies.ui.view.DetailListView;
import com.bgirlogic.movies.ui.view.ReviewRowView;
import com.bgirlogic.movies.ui.view.TrailerRowView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Senpai on 1/10/16.
 */
public class DetailedFragment extends Fragment implements DetailListView {

    @Bind(R.id.loader)
    protected ProgressBar mLoader;

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

    @Bind(R.id.mark_favorite)
    protected TextView mMarkFavorite;

    @Bind(R.id.trailer_layout)
    protected LinearLayout mTrailerLayout;

    @Bind(R.id.review_layout)
    protected LinearLayout mReviewLayout;

    private static final String TAG = DetailedFragment.class.getSimpleName();

    public static final String PARAMS_MOVIE = "PARAMS_MOVIE";

    public static final String PARAMS_MOVIE_ID = "PARAMS_MOVIE_ID";

    private View mView;

    private Movie mMovie;

    private DetailedPresenterImp mDetailedPresenterImp;

    private TrailerRowView mTrailerRow;

    private ReviewRowView mReviewRow;

    private String mId;

    private List<Trailer> mTrailers;

    private List<Review> mReviews;

    private TextView mEmptytextView;

    public static DetailedFragment newInstance(Movie movie) {
        DetailedFragment fragment = new DetailedFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAMS_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(PARAMS_MOVIE);
            if (mMovie != null) {
                mId = mMovie.getId();
            }
            mDetailedPresenterImp = new DetailedPresenterImp(this, mId);

            mTrailers = new ArrayList<>();
            mReviews = new ArrayList<>();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARAMS_MOVIE_ID, mId);
        outState.putParcelable(PARAMS_MOVIE, mMovie);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        //inject this activity's dependencies
        ((App) getActivity().getApplication()).getDaggerComponent().inject(this);

        mView = inflater.inflate(R.layout.fragment_detailed, container, false);
        ButterKnife.bind(this, mView);

        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(PARAMS_MOVIE);
        }

        if (mMovie != null) {
            Log.d(TAG, "movie is not null in detail fragment.");
            Picasso.with(getContext())
                    .load(Utils.getImageUrl(mMovie.getPosterPath())).into(mImageView);

            mTitle.setText(mMovie.getTitle());

            mReleaseDate.setText("Release date: " + mMovie.getReleaseDate());

            mVoteAverage.setText("Average vote: " + Math.round(mMovie.getmVoteAverage()) + "/10");

            mOverview.setText("Plot Synopsis: " + mMovie.getOverview());

            mMarkFavorite.setVisibility(View.VISIBLE);
        } else {
            Log.d(TAG, "movie is null in detail fragment.");
            mTitle.setText("Nothing has been selected yet");
        }
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDetailedPresenterImp.fetchTrailers(mId);
        mDetailedPresenterImp.fetchReviews(mId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showLoading() {
        if (mMovie != null) {
            mLoader.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (mMovie != null) {
            mLoader.setVisibility(View.GONE);
        }
    }

    //receives trailers from detailedPresenterImp.
    @Override
    public void setTrailers(final List<Trailer> trailers) {
        if (trailers != null) {
            mTrailerLayout.removeAllViews();
            mTrailers = trailers;
            int counter = 1;
            for (int i = 0; i < trailers.size(); i++) {
                mTrailerRow = new TrailerRowView(this.getContext(), String.valueOf(counter));
                mTrailerLayout.addView(mTrailerRow);
                final String finalKey = trailers.get(i).getKey();
                mTrailerRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + finalKey)));
                    }
                });
                counter++;
            }
        }
    }

    //receives reviews from detailedPresenterImp.
    @Override
    public void setReviews(List<Review> reviews) {
        if (reviews != null) {
            mReviewLayout.removeAllViews();
            mReviews = reviews;

            for (int i = 0; i < reviews.size(); i++) {
                String author = reviews.get(i).getAuthor();
                String content = reviews.get(i).getContent();
                final String url = reviews.get(i).getUrl();
                mReviewRow = new ReviewRowView(this.getContext(), author, content);
                mReviewLayout.addView(mReviewRow);
                mReviewRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url)));
                    }
                });
            }
        }
    }

    @Override
    public void removeTrailer(Trailer movie) {

    }

    @Override
    public void removeReview(Review movie) {

    }

    @OnClick(R.id.mark_favorite)
    protected void onClickFavorite() {
        Snackbar.make(mView,
                "Item favorited", Snackbar.LENGTH_SHORT)
                .show();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getId());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_TITLE, mMovie.getTitle());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_POSTER_PATH, mMovie.getPosterPath());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
        contentValues.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, mMovie.getmVoteAverage());
        getActivity().getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI,
                contentValues);
    }
}
