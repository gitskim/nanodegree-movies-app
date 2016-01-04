package com.bgirlogic.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bgirlogic.movies.App;
import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.Movie;
import com.bgirlogic.movies.common.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Senpai on 12/21/15.
 */
public class StaggeredViewAdapter extends RecyclerView.Adapter<StaggeredView> {

    private List<Movie> mMovies;

    private static Context sContext = App.getInstance().getApplicationContext();

    public StaggeredViewAdapter() {
        this.mMovies = null;
    }

    @Override
    public StaggeredView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        StaggeredView staggeredView = new StaggeredView(layoutView);
        return staggeredView;
    }

    @Override
    public void onBindViewHolder(StaggeredView holder, final int position) {
        Picasso.with(sContext)
                .load(Utils.getImageUrl(mMovies.get(position).getPosterPath()))
                .into(holder.mMovieThumbnail);

        holder.mMovieTitle.setText(mMovies.get(position).getTitle());

        holder.mGridItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "movieId: " + mMovies.get(position).getId());
                v.getContext().startActivity(
                        DetailedActivity.newIntent(sContext, mMovies.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public void addMovies(List<Movie> movies) {
        if (movies != null) {
            mMovies = movies;
            notifyDataSetChanged();
        }
    }
}
