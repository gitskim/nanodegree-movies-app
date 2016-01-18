package com.bgirlogic.movies.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.bgirlogic.movies.ui.presenter.DetailedPresenterImp;
import com.bgirlogic.movies.ui.view.DetailListView;
import com.bgirlogic.movies.ui.view.TrailerRowView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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

    @Bind(R.id.trailer_layout)
    protected LinearLayout mTrailerLayout;

    private static final String TAG = DetailedFragment.class.getSimpleName();

    public static final String PARAMS_MOVIE = "PARAMS_MOVIE";

    private View mView;

    private Movie mMovie;

    private DetailedPresenterImp mDetailedPresenterImp;

    private TrailerRowView mTrailerRow;

    private String mId;

    private List<Trailer> mTrailers;

    private List<Review> mReviews;

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
            mId = mMovie.getId();
        }

        mDetailedPresenterImp = new DetailedPresenterImp(this, mId);

        mTrailers = new ArrayList<>();
        mReviews = new ArrayList<>();
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

        mTitle.setText(mMovie.getTitle());

        mReleaseDate.setText("Release date: " + mMovie.getReleaseDate());

        mVoteAverage.setText("Average vote: " + Math.round(mMovie.getmVoteAverage()) + "/10");

        mOverview.setText("Plot Synopsis: " + mMovie.getOverview());
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDetailedPresenterImp.fetchTrailers(mId);
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
                final String finalId = trailers.get(i).getId();
                mTrailerRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + finalId)));
                    }
                });
                counter++;
            }
        }
    }

    //receives reviews from detailedPresenterImp.
    @Override
    public void setReviews(List<Review> reviews) {

    }

    @Override
    public void removeTrailer(Trailer movie) {

    }

    @Override
    public void removeReview(Review movie) {

    }
}
