package com.bgirlogic.movies.api;

import com.bgirlogic.movies.api.models.Movies;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

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

    private OkHttpClient mHttpClient;

    private final static String API_KEY_PARAM = "api_key";

    private RetrofitAdapter() {

        mHttpClient = new OkHttpClient();

        mHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.httpUrl().newBuilder()
                        .addQueryParameter(API_KEY_PARAM, Constants.API_KEY)
                        .build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });

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
