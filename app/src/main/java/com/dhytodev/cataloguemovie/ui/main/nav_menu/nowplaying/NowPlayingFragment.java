package com.dhytodev.cataloguemovie.ui.main.nav_menu.nowplaying;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.dhytodev.cataloguemovie.ui.main.nav_menu.MainFragment;

/**
 * Created by izadalab on 19/01/18.
 */

public class NowPlayingFragment extends MainFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.fetchMovies(0);
        refresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(false);
        presenter.fetchMovies(0);
    }
}
