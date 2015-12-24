package com.bgirlogic.movies.ui;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bgirlogic.movies.R;

/**
 * Created by Senpai on 12/21/15.
 */
public class StaggeredView extends RecyclerView.ViewHolder {

    private CardView mCardView;

    public StaggeredView(View itemView) {
        super(itemView);
        mCardView = (CardView) itemView.findViewById(R.id.card_view);
    }
}
