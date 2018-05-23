package com.dhytodev.cataloguemovie.ui.settings;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.model.MoviesResponse;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.MvpInteractor;

import java.util.List;

import io.reactivex.Observable;

public interface SettingsInteractor extends MvpInteractor<MovieService> {
    Observable<List<Movie>> fetchMovieTodayRelease();
}
