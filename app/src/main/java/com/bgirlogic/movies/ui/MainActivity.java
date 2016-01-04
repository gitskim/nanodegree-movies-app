package com.bgirlogic.movies.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.Movie;
import com.bgirlogic.movies.api.models.Movies;
import com.bgirlogic.movies.api.RetrofitAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String SORT_BY_POPULARITY = "popularity.desc";

    private static final String SORTY_BY_RATING = "vote_count.desc";

    private List<Movie> mMovies;

    private RecyclerView mRecylerView;

    private StaggeredViewAdapter mAdapter;

    private ProgressBar mLoader;

    private boolean mIsPopularitySorted;
    
    private View mMainCoordinatorLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMainCoordinatorLayoutView = findViewById(R.id.activity_main);
        mRecylerView = (RecyclerView) mMainCoordinatorLayoutView.findViewById(R.id.recycler_view);
        mRecylerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mLoader = (ProgressBar) mMainCoordinatorLayoutView.findViewById(R.id.loader);
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMovies(SORT_BY_POPULARITY);
        mIsPopularitySorted = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_popularity) {
            if (mIsPopularitySorted) {
                Snackbar.make(mMainCoordinatorLayoutView,
                        "Already sorted based on popularity", Snackbar.LENGTH_SHORT)
                .show();
                return true;
            }
            fetchMovies(SORT_BY_POPULARITY);
            mIsPopularitySorted = true;
        } else if (id == R.id.action_sort_rating) {
            if (!mIsPopularitySorted) {
                Snackbar.make(mMainCoordinatorLayoutView,
                        "Already sorted based on ranks", Snackbar.LENGTH_SHORT)
                .show();
                return true;
            }
            fetchMovies(SORTY_BY_RATING);
            mIsPopularitySorted = false;
        }

        return super.onOptionsItemSelected(item);
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

    private void initAdapter() {
        mAdapter = new StaggeredViewAdapter();
        mRecylerView.setAdapter(mAdapter);
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        mRecylerView.addItemDecoration(decoration);
    }

    private void hideLoader() {
        mLoader.setVisibility(View.GONE);
    }

    private void showLaoder() {
        mLoader.setVisibility(View.VISIBLE);
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
}
