package com.bgirlogic.movies.api.models.review;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Senpai on 1/11/16.
 */
public class Reviews implements Parcelable{

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Review> results;

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getId() {
        return id;
    }

    public List<Review> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "id=" + id +
                ", results=" + results +
                ", page=" + page +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reviews reviews = (Reviews) o;

        if (id != reviews.id) return false;
        if (page != reviews.page) return false;
        if (totalPages != reviews.totalPages) return false;
        if (totalResults != reviews.totalResults) return false;
        return !(results != null ? !results.equals(reviews.results) : reviews.results != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + page;
        result = 31 * result + totalPages;
        result = 31 * result + totalResults;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(page);
        dest.writeInt(totalPages);
        dest.writeInt(totalResults);
    }

    protected Reviews(Parcel in) {
        id = in.readInt();
        page = in.readInt();
        totalPages = in.readInt();
        totalResults = in.readInt();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}
