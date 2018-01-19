package com.dhytodev.cataloguemovie.ui.main.nav_menu.search;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.mybasemvp.MvpView;

import java.util.List;

/**
 * Created by izadalab on 19/01/18.
 */

public interface SearchView extends MvpView {
    void showLoading(boolean isShowLoading);
    void displayMovies(List<Movie> movies);
    void showStatus(boolean isDataFound);
}
