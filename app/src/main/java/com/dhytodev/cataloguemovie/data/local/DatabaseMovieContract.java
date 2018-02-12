package com.dhytodev.cataloguemovie.data.local;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by izadalab on 12/02/18.
 */

public class DatabaseMovieContract {
    public static final String TABLE_MOVIES = "table_movies";
    public static final String CONTENT_AUTHORITY = "com.dhytodev.cataloguemovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_MOVIES)
            .build();

    public static final class MovieColumns implements BaseColumns {
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_OVERVIEW = "movieOverview";
        public static final String COLUMN_MOVIE_VOTE_COUNT = "movieVoteCount";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movieVoteAverage";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_MOVIE_POPULARITY = "moviePopularity";
        public static final String COLUMN_MOVIE_POSTER_PATH = "moviePosterPath";
        public static final String COLUMN_MOVIE_BACKDROP_PATH = "movieBackdropPath";
    }
}
