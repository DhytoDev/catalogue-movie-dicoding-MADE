package com.dhytodev.cataloguemovie.ui.detail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.model.Trailer;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.BaseInteractor;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.CONTENT_URI;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.COLUMN_MOVIE_BACKDROP_PATH;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.COLUMN_MOVIE_ID;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.COLUMN_MOVIE_OVERVIEW;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.COLUMN_MOVIE_POPULARITY;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.COLUMN_MOVIE_POSTER_PATH;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.COLUMN_MOVIE_RELEASE_DATE;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.COLUMN_MOVIE_TITLE;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.COLUMN_MOVIE_VOTE_AVERAGE;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.COLUMN_MOVIE_VOTE_COUNT;

/**
 * Created by izadalab on 03/12/17.
 */

public class DetailInteractorImpl extends BaseInteractor<MovieService> implements DetailInteractor {

    private Context context ;

    public DetailInteractorImpl(MovieService service, Context context) {
        super(service);
        this.context = context;
    }

    @Override
    public Observable<List<Trailer>> getMovieTrailers(String id) {
        return getService().getMovieTrailers(id).flatMap(trailerResponse -> Observable.just(trailerResponse.getResults()));
    }

    @Override
    public Completable saveMovie(Movie movie) {
        return Completable.create(e -> {
            context.getContentResolver().insert(CONTENT_URI, setMovieContentValues(movie));
            e.onComplete();
        });
    }

    @Override
    public Completable deleteMovie(Movie movie) {
        return Completable.create(e -> {
            final String whereClause = String.format("%s=?", COLUMN_MOVIE_ID);
            final String[] args = new String[]{String.valueOf(movie.getId())};
            context.getContentResolver().delete(CONTENT_URI, whereClause, args);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> isMovieFavorited(Movie movie) {
        return Observable.create(e -> {
            final String whereClause = String.format("%s=?", COLUMN_MOVIE_ID);
            final String[] args = new String[]{String.valueOf(movie.getId())};
            final Cursor cursor = context.getContentResolver()
                    .query(CONTENT_URI, null, whereClause, args, null);

//            Log.e("count", String.valueOf(cursor.getCount()));

            if (cursor.getCount() == 0) {
                e.onNext(false);
            } else {
                e.onNext(true);
            }
            cursor.close();
        });
    }

    private static ContentValues setMovieContentValues(Movie movie) {
        final ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_ID, movie.getId());
        values.put(COLUMN_MOVIE_TITLE, movie.getTitle());
        values.put(COLUMN_MOVIE_OVERVIEW, movie.getOverview());
        values.put(COLUMN_MOVIE_VOTE_COUNT, movie.getVoteCount());
        values.put(COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(COLUMN_MOVIE_POPULARITY, movie.getPopularity());
        values.put(COLUMN_MOVIE_POSTER_PATH, movie.getPosterPath());
        values.put(COLUMN_MOVIE_BACKDROP_PATH, movie.getBackdropPath());
        return values;
    }

}
