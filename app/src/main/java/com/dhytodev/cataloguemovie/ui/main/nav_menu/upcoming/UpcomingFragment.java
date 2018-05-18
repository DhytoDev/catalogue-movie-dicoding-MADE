package com.dhytodev.cataloguemovie.ui.main.nav_menu.upcoming;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.ui.main.nav_menu.MainFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends MainFragment implements SwipeRefreshLayout.OnRefreshListener {

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            List<Movie> movies = savedInstanceState.getParcelableArrayList("movies");
            presenter.fetchMoviesFromState(movies);
        } else {
            presenter.fetchMovies(1);
        }
        refresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(false);
        presenter.fetchMovies(1);
    }

}
