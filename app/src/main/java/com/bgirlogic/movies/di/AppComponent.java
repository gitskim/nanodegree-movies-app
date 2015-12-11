package com.bgirlogic.movies.di;

import android.support.test.espresso.core.deps.dagger.Component;

import javax.inject.Singleton;

/**
 * Created by kimsuh on 12/10/15.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    //Application-level
    void inject(App application);

    //Activities

    //Fragments
}
