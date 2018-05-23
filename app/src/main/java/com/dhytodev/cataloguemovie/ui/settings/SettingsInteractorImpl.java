package com.dhytodev.cataloguemovie.ui.settings;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.model.MoviesResponse;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.BaseInteractor;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


public class SettingsInteractorImpl extends BaseInteractor<MovieService> implements SettingsInteractor {

    public SettingsInteractorImpl(MovieService service) {
        super(service);
    }

    @Override
    public Observable<List<Movie>> fetchMovieTodayRelease() {
        return getService()
                .getUpcomingMovies()
                .flatMap(moviesResponse -> Observable.just(moviesResponse.getResults()));
    }
}
