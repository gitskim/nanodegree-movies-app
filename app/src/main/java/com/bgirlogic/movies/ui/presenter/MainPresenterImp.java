package com.bgirlogic.movies.ui.presenter;

import android.database.Cursor;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.api.RetrofitAdapter;
import com.bgirlogic.movies.api.models.movie.Movie;
import com.bgirlogic.movies.api.models.movie.Movies;
import com.bgirlogic.movies.common.Utils;
import com.bgirlogic.movies.data.MoviesContract;
import com.bgirlogic.movies.ui.view.MovieListView;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Senpai on 1/10/16.
 */
public class MainPresenterImp implements Presenter<MovieListView> {

    public static final String SORT_BY_POPULARITY = "popularity.desc";

    public static final String SORTY_BY_RATING = "vote_count.desc";

    private List<Movie> mMovies;

    private MovieListView mMovieListView;

    public MainPresenterImp(MovieListView view) {
        this.mMovieListView = view;
    }

    @Override
    public void initialize() {
        mMovies = new ArrayList<>();
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void setView(MovieListView view) {

    }

    @Override
    public void onResume() {
        if (mMovieListView != null) {
            fetchMovies(SORT_BY_POPULARITY);
        }
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onDestroy() {

    }

    public void fetchMovies(String sortBy) {
        mMovieListView.showLoading();
        if (mMovies != null) {
            mMovies.clear();
        }
        RetrofitAdapter.getInstance().getMovies(sortBy)
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                        mMovieListView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Movies movies) {
                        mMovies = movies.getResults();
                        mMovies = filterMovies();
                        mMovieListView.setItems(mMovies);
                    }
                });
    }

    public void fetchMoviesFromDB() {
        mMovies.clear();
        String[] projection = {
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID,
                MoviesContract.MovieEntry.COLUMN_TITLE,
                MoviesContract.MovieEntry.COLUMN_POSTER_PATH,
                MoviesContract.MovieEntry.COLUMN_RELEASE_DATE,
                MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE};

        Cursor cursor = App.getInstance().getApplicationContext().getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                projection, null, null, null);

        assert cursor != null;
        if (cursor.getCount() == 0) {
            cursor.close();
        }

        Movie movie;
        while (cursor.moveToNext()) {
            movie = new Movie();
            movie.setId(cursor.getString(0));
            movie.setTitle(cursor.getString(1));
            movie.setPoster_path(cursor.getString(2));
            movie.setRelease_date(cursor.getString(3));
            movie.setmVoteAverage(cursor.getDouble(4));
            mMovies.add(movie);
        }
        cursor.close();

        mMovieListView.setItems(mMovies);
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
}
