package com.bgirlogic.movies.ui.presenter;

import com.bgirlogic.movies.ui.view.MovieListView;

/**
 * Created by Senpai on 1/6/16.
 */
public interface Presenter<T extends MovieListView> {

    void initialize();

    void onViewCreate();

    void onViewResume();

    void onViewDestroy();

    void setView(T view);
}
