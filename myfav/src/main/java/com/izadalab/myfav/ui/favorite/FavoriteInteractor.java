package com.izadalab.myfav.ui.favorite;

import com.dhytodev.mybasemvp.MvpInteractor;
import com.izadalab.myfav.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by izadalab on 13/02/18.
 */

public interface FavoriteInteractor extends MvpInteractor {
    Observable<List<Movie>> fetchMovieFavorites();
}
