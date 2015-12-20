package com.bgirlogic.movies.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kimsuh on 12/18/15.
 */
public class Movies {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movies movies = (Movies) o;

        if (page != movies.page) return false;
        return !(results != null ? !results.equals(movies.results) : movies.results != null);

    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "page=" + page +
                ", results=" + results +
                '}';
    }
}
