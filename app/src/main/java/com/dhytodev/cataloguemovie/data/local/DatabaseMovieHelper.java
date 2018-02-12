package com.dhytodev.cataloguemovie.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.*;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.TABLE_MOVIES;

/**
 * Created by izadalab on 12/02/18.
 */

public class DatabaseMovieHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "catalogue-movies";
    private static final int DB_VERSION = 1;

    private static final String CREATE_MOVIE_TABLE = String.format("CREATE TABLE %s " +
                    "(%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL, %s TEXT NOT NULL, %s TEXT, " +
                    "%s REAL, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s REAL, UNIQUE (%s) ON CONFLICT REPLACE)",
            TABLE_MOVIES, _ID, COLUMN_MOVIE_ID, COLUMN_MOVIE_TITLE, COLUMN_MOVIE_OVERVIEW, COLUMN_MOVIE_VOTE_AVERAGE,
            COLUMN_MOVIE_VOTE_COUNT, COLUMN_MOVIE_BACKDROP_PATH, COLUMN_MOVIE_POSTER_PATH, COLUMN_MOVIE_RELEASE_DATE,
            COLUMN_MOVIE_POPULARITY, COLUMN_MOVIE_ID);

    private Context context ;

    public DatabaseMovieHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }
}
