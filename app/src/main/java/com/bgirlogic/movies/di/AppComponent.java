package com.bgirlogic.movies.di;


import com.bgirlogic.movies.App;
import com.bgirlogic.movies.ui.StaggeredViewAdapter;
import com.bgirlogic.movies.ui.activity.DetailedActivity;
import com.bgirlogic.movies.ui.activity.MainActivity;
import com.bgirlogic.movies.ui.fragment.DetailedFragment;
import com.bgirlogic.movies.ui.fragment.MainFragment;
import com.bgirlogic.movies.ui.view.TrailerRowView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kimsuh on 12/10/15.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    //Application-level
    void inject(App application);

    //Activities
    void inject(MainActivity activity);

    void inject(DetailedActivity activity);

    //Fragments
    void inject(MainFragment fragment);

    void inject(DetailedFragment fragment);

    //view
    void inject(TrailerRowView view);
}
