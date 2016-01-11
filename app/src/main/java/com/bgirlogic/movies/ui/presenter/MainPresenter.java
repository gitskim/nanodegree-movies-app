package com.bgirlogic.movies.ui.presenter;

import com.bgirlogic.movies.ui.view.MovieListView;

/**
 * Created by Senpai on 1/6/16.
 */
public interface MainPresenter extends Presenter<MovieListView> {

    void onResume();

    void onItemClicked(int position);

    void onDestroy();
}
