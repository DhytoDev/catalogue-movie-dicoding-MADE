package com.dhytodev.cataloguemovie.ui.main.upcoming;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.ui.main.MainFragment;

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
        presenter.fetchMovies(1);
        refresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(false);
        presenter.fetchMovies(1);
    }

}
