package com.dhytodev.cataloguemovie.ui.main.nav_menu.search;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.MvpInteractor;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by izadalab on 19/01/18.
 */

public interface SearchInteractor extends MvpInteractor<MovieService> {
    Observable<List<Movie>> fetchSearchMovies(String movieName);
}
