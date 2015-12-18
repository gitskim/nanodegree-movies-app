package com.bgirlogic.movies.api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Senpai on 12/13/15.
 */
public class RetrofitAdapter {

    private static RetrofitAdapter mInstance = null;

    private APIService mApiService;

    private OkHttpClient mHttpClient = new OkHttpClient();

    private RetrofitAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mHttpClient)
                .build();

        mApiService = retrofit.create(APIService.class);
    }

    public static RetrofitAdapter getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitAdapter();
        }
        return mInstance;
    }

    public Observable<Movies> getMovies(String sortBy) {
        return mApiService.getMovies(sortBy).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

}
