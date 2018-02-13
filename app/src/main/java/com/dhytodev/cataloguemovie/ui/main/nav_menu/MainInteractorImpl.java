package com.dhytodev.cataloguemovie.ui.main.nav_menu;

import android.content.Context;
import android.database.Cursor;

import com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract;
import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.BaseInteractor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.CONTENT_URI;

/**
 * Created by izadalab on 03/12/17.
 */

public class MainInteractorImpl extends BaseInteractor<MovieService> implements MainInteractor {

    private Context context ;

    public MainInteractorImpl(MovieService service) {
        super(service);
    }

    public MainInteractorImpl(MovieService service, Context context) {
        super(service);
        this.context = context;
    }

    @Override
    public Observable<List<Movie>> fetchUpComingMovies() {
        return getService().getUpcomingMovies().flatMap(moviesResponse -> Observable.just(moviesResponse.results));
    }

    @Override
    public Observable<List<Movie>> fetchNowPlayingMovies() {
        return getService().getNowPlayingMovies().flatMap(moviesResponse -> Observable.just(moviesResponse.results));
    }

    @Override
    public Observable<List<Movie>> fetchFavoriteMovies() {
        return Observable.create(e -> {
            List<Movie> movies = new ArrayList<>();
            final Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null,null);

            if (cursor.moveToFirst()) {
                do {
                    movies.add(new Movie(cursor));
                } while (cursor.moveToNext());
            }

            cursor.close();
            e.onNext(movies);
        });
    }
}
