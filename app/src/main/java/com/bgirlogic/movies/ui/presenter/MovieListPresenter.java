package com.bgirlogic.movies.ui.presenter;

import com.bgirlogic.movies.api.models.Movies;
import com.bgirlogic.movies.ui.view.MovieListView;

/**
 * Created by Senpai on 1/6/16.
 */
public interface MovieListPresenter extends Presenter<MovieListView> {

    Movies getParcelableCollection();

}
