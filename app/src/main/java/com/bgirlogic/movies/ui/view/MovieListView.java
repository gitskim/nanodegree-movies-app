package com.bgirlogic.movies.ui.view;

import com.bgirlogic.movies.api.models.movie.Movie;

import java.util.List;

/**
 * Created by Senpai on 1/6/16.
 */
public interface MovieListView extends DevilListView{

    void showLoading();

    void hideLoading();

    void setItems(List<Movie> movies);

    void remove(Movie movie);
}
