package com.bgirlogic.movies.api;

import com.bgirlogic.movies.api.models.Movie;
import com.bgirlogic.movies.api.models.Movies;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Senpai on 12/13/15.
 */
public interface APIService {

    //http://api.themoviedb.org/3/discover/movie?sort_by=popularity_desc&api_key=8df5a981e3421ec1fe8fb26bc13e368b
    @GET(Constants.API_ADDRESS_ENDPOINT_MOVIES)
    Observable<Movies> getMovies(@Query("sort_by") String sortBy);

    //http://api.themoviedb.org/3/movie/23800?api_key=8df5a981e3421ec1fe8fb26bc13e368b
    @GET(Constants.API_ADDRESS_ENDPOINT_DETAILS)
    Observable<Movie> getMovieDetails(@Path("id") String id);
}
