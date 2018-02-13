package com.izadalab.myfav.ui.favorite;

import com.dhytodev.mybasemvp.MvpView;
import com.izadalab.myfav.data.model.Movie;

import java.util.List;

/**
 * Created by izadalab on 13/02/18.
 */

public interface FavoriteView extends MvpView {
    void showLoading(boolean isShowLoading);
    void displayMovies(List<Movie> movies);
}
