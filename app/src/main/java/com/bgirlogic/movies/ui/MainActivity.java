package com.bgirlogic.movies.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.Movie;
import com.bgirlogic.movies.api.models.Movies;
import com.bgirlogic.movies.api.RetrofitAdapter;

import java.util.List;

import butterknife.Bind;
import rx.Observer;

public class MainActivity extends AppCompatActivity {

    private List<Movie> mMovies;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecylerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecylerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        StaggeredViewAdapter adapter = new StaggeredViewAdapter(this, mMovies);
        mRecylerView.setAdapter(adapter);
        SpaceItemDecoration decoration = new SpaceItemDecoration(16);
        mRecylerView.addItemDecoration(decoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMovies("popularity_desc");
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchMovies(String sortBy) {
        RetrofitAdapter.getInstance().getMovies(sortBy)
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG", "completed ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "onError " + e.getMessage());
                    }

                    @Override
                    public void onNext(Movies movies) {
                        mMovies = movies.getResults();
                        Log.e("TAG", "result is: " + mMovies);
                    }
                });
    }
}
