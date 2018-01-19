package com.dhytodev.cataloguemovie.ui.main.nav_menu.search;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.BaseInteractor;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by izadalab on 19/01/18.
 */

public class SearchInteractorImpl extends BaseInteractor<MovieService> implements SearchInteractor {

    public SearchInteractorImpl(MovieService service) {
        super(service);
    }

    @Override
    public Observable<List<Movie>> fetchSearchMovies(String movieName) {
        return getService().searchMovies(movieName).flatMap(moviesResponse -> Observable.just(moviesResponse.results));
    }
}
