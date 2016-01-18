package com.bgirlogic.movies.ui.view;

import com.bgirlogic.movies.api.models.review.Review;
import com.bgirlogic.movies.api.models.trailer.Trailer;

import java.util.List;

/**
 * Created by Senpai on 1/6/16.
 */
public interface DetailListView extends DevilListView {

    void showLoading();

    void hideLoading();

    void setTrailers(List<Trailer> trailers);

    void setReviews(List<Review> reviews);

    void removeTrailer(Trailer movie);

    void removeReview(Review movie);
}
