package com.bgirlogic.movies.ui;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgirlogic.movies.R;

/**
 * Created by Senpai on 12/21/15.
 * <p/>
 * view holder
 */
public class StaggeredView extends RecyclerView.ViewHolder {

    private CardView mCardView;

    public ImageView mMovieThumbnail;

    public TextView mMovieTitle;

    public View mGridItem;

    public StaggeredView(View itemView) {
        super(itemView);
        mCardView = (CardView) itemView.findViewById(R.id.card_view);
        mMovieThumbnail = (ImageView) itemView.findViewById(R.id.movie_thumbnail);
        mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
        mGridItem = itemView.findViewById(R.id.grid_item);
    }


}
