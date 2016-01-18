package com.bgirlogic.movies.ui.presenter;

import android.util.Log;

import com.bgirlogic.movies.api.RetrofitAdapter;
import com.bgirlogic.movies.api.models.movie.Movie;
import com.bgirlogic.movies.api.models.movie.Movies;
import com.bgirlogic.movies.api.models.review.Reviews;
import com.bgirlogic.movies.api.models.trailer.Trailers;
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
        RetrofitAdapter.getInstance().getMovies(sortBy)
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Movies movies) {
                        mMovieListView.hideLoading();
                        mMovies = movies.getResults();
                        mMovies = filterMovies();
                        mMovieListView.setItems(mMovies);
                    }
                });
    }

    public void fetchTrailers(String id) {
        RetrofitAdapter.getInstance().getTrailers(id)
                .subscribe(new Observer<Trailers>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TAG fuckyou", "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG fuckyou", "error " + e.getMessage());
                    }

                    @Override
                    public void onNext(Trailers trailers) {
                        Log.d("TAG", "fuckyou onnext " + trailers.getResults());
                    }
                });
    }

    public void fetchReviews(String id) {
        RetrofitAdapter.getInstance().getReviews(id)
                .subscribe(new Observer<Reviews>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TAG fuckyou", "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG fuckyou", "error " + e.getMessage());
                    }

                    @Override
                    public void onNext(Reviews trailers) {
                        Log.d("TAG", "fuckyou onnext " + trailers.getResults());
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
}
