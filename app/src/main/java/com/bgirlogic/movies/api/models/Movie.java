package com.bgirlogic.movies.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kimsuh on 12/18/15.
 */
public class Movie {

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("adult")
    private boolean adult;

    public String getPosterPath() {
        return poster_path;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public boolean isAdult() {
        return adult;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", adult=" + adult +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (adult != movie.adult) return false;
        if (poster_path != null ? !poster_path.equals(movie.poster_path) : movie.poster_path != null)
            return false;
        if (release_date != null ? !release_date.equals(movie.release_date) : movie.release_date != null)
            return false;
        if (id != null ? !id.equals(movie.id) : movie.id != null) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        return !(overview != null ? !overview.equals(movie.overview) : movie.overview != null);

    }

    @Override
    public int hashCode() {
        int result = poster_path != null ? poster_path.hashCode() : 0;
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (adult ? 1 : 0);
        return result;
    }
}