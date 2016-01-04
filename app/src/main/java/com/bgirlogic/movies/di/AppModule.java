package com.bgirlogic.movies.di;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kimsuh on 12/10/15.
 */

@Module
public class AppModule {

    private final Context appContext;

    public AppModule(Application application) {
        this.appContext = application.getApplicationContext();
    }

    @Provides
    public Context provideApplicationContext() {
        return appContext;
    }
}
