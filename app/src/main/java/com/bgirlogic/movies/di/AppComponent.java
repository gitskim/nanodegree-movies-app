package com.bgirlogic.movies.di;


import com.bgirlogic.movies.MainActivity;

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

    //Fragments
}