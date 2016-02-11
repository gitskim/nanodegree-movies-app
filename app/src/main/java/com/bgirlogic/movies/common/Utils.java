package com.bgirlogic.movies.common;

import android.content.res.Configuration;
import android.text.TextUtils;

import com.bgirlogic.movies.App;
import com.jakewharton.rxbinding.internal.Preconditions;

import javax.inject.Singleton;

/**
 * Created by kimsuh on 12/24/15.
 */
@Singleton
public class Utils {

    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w";

    public static final Integer IMAGE_SIZE = 185;

    public static String getImageUrl(String imgPath) {
        Preconditions.checkArgument(!TextUtils.isEmpty(imgPath), "img path is empty");
        StringBuilder uriString = new StringBuilder(BASE_IMAGE_URL);
        uriString.append(IMAGE_SIZE);
        uriString.append(imgPath);
        return uriString.toString();
    }

    public static boolean isLandscape() {
        return App.getInstance().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
