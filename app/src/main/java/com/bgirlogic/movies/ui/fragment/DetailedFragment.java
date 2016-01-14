package com.bgirlogic.movies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.movie.Movie;
import com.bgirlogic.movies.api.models.review.Review;
import com.bgirlogic.movies.api.models.trailer.Trailer;
import com.bgirlogic.movies.common.Utils;
import com.bgirlogic.movies.ui.view.DetailListView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    private static final String TAG = DetailedFragment.class.getSimpleName();

    public static final String PARAMS_MOVIE = "PARAMS_MOVIE";

    private View mView;

    private Movie mMovie;

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
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_detailed, container, false);
        ButterKnife.bind(this, mView);

        //inject this activity's dependencies
        ((App) getActivity().getApplication()).getDaggerComponent().inject(this);

        Picasso.with(getContext())
                .load(Utils.getImageUrl(mMovie.getPosterPath())).into(mImageView);

        mTitle.setText("Title: " + mMovie.getTitle());

        mReleaseDate.setText("Release date: " + mMovie.getReleaseDate());

        mVoteAverage.setText("Average vote: " + Math.round(mMovie.getmVoteAverage()));

        mOverview.setText("Plot Synopsis: " + mMovie.getOverview());
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showLoading() {
        mLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoader.setVisibility(View.GONE);
    }

    @Override
    public void setTrailers(List<Trailer> movies) {

    }

    @Override
    public void setReviews(List<Review> movies) {

    }

    @Override
    public void removeTrailer(Trailer movie) {

    }

    @Override
    public void removeReview(Review movie) {

    }
}
