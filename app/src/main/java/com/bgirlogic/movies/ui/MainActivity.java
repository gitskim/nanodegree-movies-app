package com.bgirlogic.movies.ui;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View mParentView = findViewById(R.id.activity_main);
        mRecylerView = (RecyclerView) mParentView.findViewById(R.id.recycler_view);
        mRecylerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mLoader = (ProgressBar) mParentView.findViewById(R.id.loader);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMovies(SORT_BY_POPULARITY);
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
            fetchMovies(SORT_BY_POPULARITY);
        } else if (id == R.id.action_sort_rating) {
            fetchMovies(SORTY_BY_RATING);
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
                        initAdapter();
                        updateAdapter();
                    }
                });
    }

    private void initAdapter() {
        mAdapter = new StaggeredViewAdapter(this, mMovies);
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
        mAdapter.notifyDataSetChanged();
    }
}
