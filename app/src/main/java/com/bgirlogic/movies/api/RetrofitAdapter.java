package com.bgirlogic.movies.api;

import com.bgirlogic.movies.api.models.movie.Movies;
import com.bgirlogic.movies.api.models.review.Reviews;
import com.bgirlogic.movies.api.models.trailer.Trailers;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

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

    private final String MIN_VOTE_COUNT_PARAM = "vote_count.gte";

    private final String MIN_VOTE_COUNT_PARAM_VALUE = "1000";

    private final static String API_KEY_PARAM = "api_key";

    private static RetrofitAdapter mInstance = null;

    private APIService mApiService;

    private OkHttpClient mHttpClient;

    private RetrofitAdapter() {

        mHttpClient = new OkHttpClient();

        mHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.httpUrl().newBuilder()
                        .addQueryParameter(MIN_VOTE_COUNT_PARAM, MIN_VOTE_COUNT_PARAM_VALUE)
                        .addQueryParameter(API_KEY_PARAM, Constants.API_KEY)
                        .build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        mHttpClient.interceptors().add(loggingInterceptor);

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

    public Observable<Trailers> getTrailers(String id) {
        return mApiService.getTrailers(id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<Reviews> getReviews(String id) {
        return mApiService.getReviews(id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

}
