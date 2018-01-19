package com.dhytodev.cataloguemovie.ui.main.nav_menu;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.mybasemvp.MvpView;

import java.util.List;

/**
 * Created by izadalab on 03/12/17.
 */

public interface MainView extends MvpView {
    void showLoading(boolean isShowLoading);
    void displayMovies(List<Movie> movies);
}
