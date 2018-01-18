package com.dhytodev.cataloguemovie.ui.main;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.cataloguemovie.ui.main.MainInteractor;
import com.dhytodev.mybasemvp.BaseInteractor;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by izadalab on 03/12/17.
 */

public class MainInteractorImpl extends BaseInteractor<MovieService> implements MainInteractor {

    public MainInteractorImpl(MovieService service) {
        super(service);
    }

    @Override
    public Observable<List<Movie>> fetchSearchMovies(String movieName) {
        return getService().searchMovies(movieName).flatMap(moviesResponse -> Observable.just(moviesResponse.results));
    }

    @Override
    public Observable<List<Movie>> fetchUpComingMovies() {
        return getService().getUpcomingMovies().flatMap(moviesResponse -> Observable.just(moviesResponse.results));
    }

    @Override
    public Observable<List<Movie>> fetchNowPlayingMovies() {
        return getService().getNowPlayingMovies().flatMap(moviesResponse -> Observable.just(moviesResponse.results));
    }
}
