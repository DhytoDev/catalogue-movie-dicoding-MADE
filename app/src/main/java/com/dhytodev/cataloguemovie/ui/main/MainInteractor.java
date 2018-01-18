package com.dhytodev.cataloguemovie.ui.main;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.MvpInteractor;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by izadalab on 03/12/17.
 */

public interface MainInteractor extends MvpInteractor<MovieService> {

    Observable<List<Movie>> fetchSearchMovies(String movieName);
    Observable<List<Movie>> fetchUpComingMovies();
    Observable<List<Movie>> fetchNowPlayingMovies();

}
