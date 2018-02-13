package com.dhytodev.cataloguemovie.ui.detail;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.model.Trailer;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.MvpInteractor;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/**
 * Created by izadalab on 03/12/17.
 */

public interface DetailInteractor extends MvpInteractor<MovieService> {
    Observable<List<Trailer>> getMovieTrailers(String id);
    Completable saveMovie(Movie movie);
    Completable deleteMovie(Movie movie);
    Observable<Boolean> isMovieFavorited(Movie movie);
}
