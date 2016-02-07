package com.bgirlogic.movies.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kimsuh on 1/19/16.
 */
public class MoviesDBHelper extends SQLiteOpenHelper {
    //name & version
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MoviesDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public MoviesDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE "
                + MoviesContract.MovieEntry.TABLE_MOVIES + "("
                + MoviesContract.MovieEntry._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " TEXT, "
                + MoviesContract.MovieEntry.COLUMN_TITLE + " TEXT, "
                + MoviesContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT, "
                + MoviesContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT, "
                + MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL);";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_MOVIES);
        db.execSQL("DELETE FROM SQLITE SQUENCE WHERE NAME = '"
                + MoviesContract.MovieEntry.TABLE_MOVIES + "'");

        onCreate(db);
    }
}
