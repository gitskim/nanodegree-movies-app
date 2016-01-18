package com.bgirlogic.movies.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bgirlogic.movies.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Senpai on 1/18/16.
 */
public class TrailerRowView extends RelativeLayout {

    @Bind(R.id.trailer_number)
    protected TextView mTrailerNumber;

    private String mNumber;

    public TrailerRowView(Context context, String trailerNumber) {
        super(context);
        init();
        updateView(trailerNumber);
    }

    public TrailerRowView(Context context, AttributeSet attrs, String trailerNumber) {
        super(context, attrs);
        init();
        updateView(trailerNumber);
    }

    public TrailerRowView(Context context, AttributeSet attrs, int defStyleAttr,
                          String trailerNumber) {
        super(context, attrs, defStyleAttr);
        init();
        updateView(trailerNumber);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TrailerRowView(Context context, AttributeSet attrs, int defStyleAttr,
                          int defStyleRes, String trailerNumber) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        updateView(trailerNumber);
    }

    private void init() {
        inflate(getContext(), R.layout.row_trailer, this);
        ButterKnife.bind(this);
    }

    private void updateView(String number) {
        mNumber = number;
        mTrailerNumber.setText("Trailer " + mNumber);
    }
}
