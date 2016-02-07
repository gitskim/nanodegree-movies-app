package com.bgirlogic.movies.ui.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.movie.Movie;
import com.bgirlogic.movies.data.MoviesContract;
import com.bgirlogic.movies.ui.SpaceItemDecoration;
import com.bgirlogic.movies.ui.StaggeredViewAdapter;
import com.bgirlogic.movies.ui.presenter.MainPresenterImp;
import com.bgirlogic.movies.ui.view.MovieListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Senpai on 1/10/16.
 */
public class MainFragment extends Fragment implements MovieListView {

    private static final String TAG = MainFragment.class.getSimpleName();

    private View mView;

    private List<Movie> mMovies;

    private boolean mIsPopularitySorted, mIsRankSorted, mIsFavoritSorted;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecylerView;

    @Bind(R.id.loader)
    protected ProgressBar mLoader;

    private StaggeredViewAdapter mAdapter;

    private MainPresenterImp mMainPresenterImp;

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
        setHasOptionsMenu(true);
        mMainPresenterImp = new MainPresenterImp(this);
        mMovies = new ArrayList<>();
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
        mMainPresenterImp.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_popularity:
                if (mIsPopularitySorted) {
                    Snackbar.make(mView,
                            "Already sorted based on popularity", Snackbar.LENGTH_SHORT)
                            .show();
                    return true;
                }
                mMainPresenterImp.fetchMovies(MainPresenterImp.SORT_BY_POPULARITY);
                mIsPopularitySorted = true;
                mIsRankSorted = false;
                mIsFavoritSorted = false;
                break;
            case R.id.action_sort_rating:
                if (mIsRankSorted) {
                    Snackbar.make(mView,
                            "Already sorted based on ranks", Snackbar.LENGTH_SHORT)
                            .show();
                    return true;
                }
                mMainPresenterImp.fetchMovies(MainPresenterImp.SORTY_BY_RATING);
                mIsRankSorted = true;
                mIsFavoritSorted = false;
                mIsPopularitySorted = false;
                break;
            case R.id.action_sort_favorite:
                if (mIsPopularitySorted) {
                    Snackbar.make(mView,
                            "Already sorted based on favorites", Snackbar.LENGTH_SHORT)
                            .show();
                    return true;
                }
                mMainPresenterImp.fetchMoviesFromDB();
                if (mMovies.size() == 0) {
                    Snackbar.make(mView,
                            "No items are favorited. Popular items are shown instead.", Snackbar.LENGTH_SHORT)
                            .show();
                    mMainPresenterImp.fetchMovies(MainPresenterImp.SORT_BY_POPULARITY);
                    mIsPopularitySorted = true;
                    mIsFavoritSorted = false;
                    mIsRankSorted = false;
                } else {
                    mIsFavoritSorted = true;
                    mIsPopularitySorted = false;
                    mIsRankSorted = false;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAdapter() {
        mAdapter = new StaggeredViewAdapter();
        mRecylerView.setAdapter(mAdapter);
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        mRecylerView.addItemDecoration(decoration);
    }

    private void updateAdapter() {
        if (mMovies != null) {
            mAdapter.addMovies(mMovies);
            mAdapter.notifyDataSetChanged();
        }
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
    public void setItems(List<Movie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        updateAdapter();
    }

    @Override
    public void remove(Movie movie) {

    }
}
