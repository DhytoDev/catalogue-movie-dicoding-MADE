package com.dhytodev.cataloguemovie.ui.home;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
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
}
