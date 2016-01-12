package com.bgirlogic.movies.api.models.trailer;

import android.os.Parcel;
import android.os.Parcelable;

import com.bgirlogic.movies.api.models.review.Review;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Senpai on 1/11/16.
 */
public class Trailers implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Review> results;

    public int getId() {
        return id;
    }

    public List<Review> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "Trailers{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trailers trailers = (Trailers) o;

        if (id != trailers.id) return false;
        return !(results != null ? !results.equals(trailers.results) : trailers.results != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }

    protected Trailers(Parcel in) {
        id = in.readInt();
    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };
}
