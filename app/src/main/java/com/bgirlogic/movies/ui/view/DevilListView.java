package com.bgirlogic.movies.ui.view;

import com.bgirlogic.movies.api.models.Movie;

import java.util.List;

/**
 * Created by Senpai on 1/10/16.
 */
public interface DevilListView {

    void setItems(List<Movie> movies);

    void remove(Movie movie);
}
