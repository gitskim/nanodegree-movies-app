package com.bgirlogic.movies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bgirlogic.movies.R;
import com.bgirlogic.movies.api.models.movie.Movie;

/**
 * Created by kimsuh on 2/10/16.
 */
public class EmptyFragment extends Fragment {

    private View mView;

    public static EmptyFragment newInstance() {
        EmptyFragment fragment = new EmptyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_empty, container, false);
        return mView;
    }
}
