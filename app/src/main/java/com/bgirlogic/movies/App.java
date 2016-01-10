package com.bgirlogic.movies;

import android.app.Application;

import com.bgirlogic.movies.di.AppComponent;
import com.bgirlogic.movies.di.AppModule;

/**
 * Created by kimsuh on 12/10/15.
 */
public class App extends Application {

    public static final String TAG = App.class.getSimpleName();

    private static App sInstance;

    /**
     * Dagger component for the application
     */
    private AppComponent appComponent;

    public App() {
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);
    }

    public AppComponent getDaggerComponent() {
        return appComponent;
    }
}
