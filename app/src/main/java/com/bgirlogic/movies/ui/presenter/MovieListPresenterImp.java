package com.bgirlogic.movies.ui.presenter;

import android.content.Context;

import com.bgirlogic.movies.api.models.Movies;
import com.bgirlogic.movies.ui.view.MovieListView;

/**
 * Created by Senpai on 1/10/16.
 */
public class MovieListPresenterImp implements MovieListPresenter {

    private Movies mMovies;

    public MovieListPresenterImp(Movies movies){
        this.mMovies = new Movies();
        this.mMovies = movies;
    };


    @Override
    public Movies getParcelableCollection() {
        return null;
    }

    @Override
    public void initialize() {
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
}
