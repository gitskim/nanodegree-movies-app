package com.bgirlogic.movies.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bgirlogic.movies.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Senpai on 1/18/16.
 */
public class ReviewRowView extends LinearLayout {

    @Bind(R.id.review_author)
    protected TextView mAuthorView;

    @Bind(R.id.review_content)
    protected TextView mContentView;

    @Bind(R.id.review_open)
    protected TextView mOpenView;

    private String mAuthor;

    private String mContent;

    public ReviewRowView(Context context, String author, String content) {
        super(context);
        mAuthor = author;
        mContent = content;
        init();
        updateView();
    }

    public ReviewRowView(Context context, AttributeSet attrs, String author, String content) {
        super(context, attrs);
        init();
        updateView();
    }

    public ReviewRowView(Context context, AttributeSet attrs, int defStyleAttr, String author,
                         String content) {
        super(context, attrs, defStyleAttr);
        init();
        updateView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ReviewRowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes,
                         String author, String content) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        updateView();
    }

    private void init() {
        inflate(getContext(), R.layout.row_review, this);
        ButterKnife.bind(this);
    }

    private void updateView() {
        mAuthorView.setText(mAuthor);
        mContentView.setText(mContent);
    }
}
