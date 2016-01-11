package com.bgirlogic.movies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.RetrofitAdapter;
import com.bgirlogic.movies.api.models.Movie;
import com.bgirlogic.movies.api.models.Movies;
import com.bgirlogic.movies.ui.SpaceItemDecoration;
import com.bgirlogic.movies.ui.StaggeredViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * Created by Senpai on 1/10/16.
 */
public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private static final String SORT_BY_POPULARITY = "popularity.desc";

    private static final String SORTY_BY_RATING = "vote_count.desc";

    private View mView;

    private List<Movie> mMovies;

    private boolean mIsPopularitySorted;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecylerView;

    @Bind(R.id.loader)
    protected ProgressBar mLoader;

    private StaggeredViewAdapter mAdapter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        //no args
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, mView);

        //inject this activity's dependencies
        ((App) getActivity().getApplication()).getDaggerComponent().inject(this);

        mRecylerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initAdapter();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchMovies(SORT_BY_POPULARITY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_popularity) {
            if (mIsPopularitySorted) {
                Snackbar.make(mView,
                        "Already sorted based on popularity", Snackbar.LENGTH_SHORT)
                        .show();
                return true;
            }
            fetchMovies(SORT_BY_POPULARITY);
            mIsPopularitySorted = true;
        } else if (id == R.id.action_sort_rating) {
            if (!mIsPopularitySorted) {
                Snackbar.make(mView,
                        "Already sorted based on ranks", Snackbar.LENGTH_SHORT)
                        .show();
                return true;
            }
            fetchMovies(SORTY_BY_RATING);
            mIsPopularitySorted = false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initAdapter() {
        mAdapter = new StaggeredViewAdapter();
        mRecylerView.setAdapter(mAdapter);
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        mRecylerView.addItemDecoration(decoration);
    }

    private void fetchMovies(String sortBy) {
        showLaoder();
        RetrofitAdapter.getInstance().getMovies(sortBy)
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "completed ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError " + e.getMessage());
                    }

                    @Override
                    public void onNext(Movies movies) {
                        hideLoader();
                        mMovies = movies.getResults();
                        mMovies = filterMovies();
                        Log.e(TAG, "result is: " + mMovies);
                        updateAdapter();
                    }
                });
    }
    private List<Movie> filterMovies() {
        List<Movie> newMovies = new ArrayList<Movie>();
        for (int i = 0; i < mMovies.size(); i++) {
            if (mMovies.get(i).getPosterPath() != null) {
                newMovies.add(mMovies.get(i));
            }
        }
        return newMovies;
    }

    private void updateAdapter() {
        if (mMovies != null) {
            mAdapter.addMovies(mMovies);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void hideLoader() {
        mLoader.setVisibility(View.GONE);
    }

    private void showLaoder() {
        mLoader.setVisibility(View.VISIBLE);
    }
}
