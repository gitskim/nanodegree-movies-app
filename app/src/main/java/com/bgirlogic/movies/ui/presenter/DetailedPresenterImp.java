package com.bgirlogic.movies.ui.presenter;

import android.util.Log;

import com.bgirlogic.movies.api.RetrofitAdapter;
import com.bgirlogic.movies.api.models.review.Review;
import com.bgirlogic.movies.api.models.review.Reviews;
import com.bgirlogic.movies.api.models.trailer.Trailer;
import com.bgirlogic.movies.api.models.trailer.Trailers;
import com.bgirlogic.movies.ui.view.DetailListView;
import com.bgirlogic.movies.ui.view.TrailerRowView;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * Created by Senpai on 1/17/16.
 */
public class DetailedPresenterImp implements Presenter<DetailListView> {

    private DetailListView mDetailListView;

    private List<Review> mReviews;

    private List<Trailer> mTrailers;

    private String mId;

    public DetailedPresenterImp(DetailListView detailListView, String id) {
        mDetailListView = detailListView;
        mId = id;
    }

    @Override
    public void initialize() {
        mReviews = new ArrayList<>();
        mTrailers = new ArrayList<>();
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void setView(DetailListView view) {

    }

    @Override
    public void onResume() {
        fetchReviews(mId);
        fetchTrailers(mId);
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onDestroy() {

    }

    public void fetchReviews(String id) {
        mDetailListView.showLoading();
        RetrofitAdapter.getInstance().getReviews(id)
                .subscribe(new Observer<Reviews>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TAG fuckyou", "completed");
                        mDetailListView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG fuckyou", "error " + e.getMessage());
                    }

                    @Override
                    public void onNext(Reviews reviews) {
                        Log.d("TAG", "fuckyou onnext " + reviews.getResults());
                        mReviews = reviews.getResults();
                        mDetailListView.setReviews(mReviews);
                    }
                });
    }

    public void fetchTrailers(String id) {
        mDetailListView.showLoading();
        RetrofitAdapter.getInstance().getTrailers(id)
                .subscribe(new Observer<Trailers>() {
                    @Override
                    public void onCompleted() {
                        mDetailListView.hideLoading();
                        Log.d("TAG fuckyou", "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG fuckyou", "error " + e.getMessage());
                    }

                    @Override
                    public void onNext(Trailers trailers) {
                        Log.d("TAG", "fuckyou onnext " + trailers.getResults());
                        mTrailers = trailers.getResults();
                        mDetailListView.setTrailers(mTrailers);
                    }
                });
    }
}
