package com.bgirlogic.movies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {

	public static final String CONTENT_AUTHORITY = "com.bgirlogic.movies.movies.app";

	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	public static final class MovieEntry implements BaseColumns {
		// table name
		public static final String TABLE_MOVIES = "movie";
		// columns
		public static final String _ID = "_id";
		public static final String COLUMN_POSTER_PATH = "poster_path";
		public static final String COLUMN_RELEASE_DATE = "release_date";
		public static final String COLUMN_TITLE = "title";
		public static final Double COLUMN_VOTE_AVERAGE = 0.0;

		// create content uri
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
			.appendPath(TABLE_MOVIES).build();
		// create cursor of base type directory for multiple entries
		public static final String CONTENT_DIR_TYPE =
		ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;
		// create cursor of base type item for single entry
		public static final String CONTENT_ITEM_TYPE =
			ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

		// for building URIs on insertion
		public static Uri buildMoviesUri(long id){
        		return ContentUris.withAppendedId(CONTENT_URI, id);
		}
	}
}
