package com.bgirlogic.movies.di;


import com.bgirlogic.movies.App;
import com.bgirlogic.movies.ui.DetailedActivity;
import com.bgirlogic.movies.ui.MainActivity;

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
}
