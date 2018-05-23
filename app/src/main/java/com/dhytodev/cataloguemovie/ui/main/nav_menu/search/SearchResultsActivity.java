package com.dhytodev.cataloguemovie.ui.main.nav_menu.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.cataloguemovie.ui.detail.DetailActivity;
import com.dhytodev.cataloguemovie.ui.main.nav_menu.MovieViewHolder;
import com.dhytodev.mybasemvp.BaseActivity;
import com.dhytodev.mybasemvp.BaseRvAdapter;
import com.dhytodev.mybasemvp.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class SearchResultsActivity extends BaseActivity implements RecyclerViewItemClickListener, SearchView {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.status_layout)
    LinearLayout statusLayout;

    private SearchInteractor interactor;
    private SearchPresenter presenter;

    private List<Movie> movies = new ArrayList<>();
    private BaseRvAdapter<Movie, MovieViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setUnbinder(ButterKnife.bind(this));
        setUp();

        presenter.onAttach(this);

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = getIntent().getStringExtra(SearchManager.QUERY);
            presenter.getSearchMovies(query);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) movies);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        List<Movie> movies = savedInstanceState.getParcelableArrayList("movies");
        presenter.getSearchMoviesFromState(movies);
    }

    @Override
    protected void setUp() {

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(getString(R.string.search_results));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        interactor = new SearchInteractorImpl(MovieService.ServiceGenerator.instance());
        presenter = new SearchPresenter(interactor, new CompositeDisposable());


        rvMovies.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMovies.setLayoutManager(layoutManager);

        adapter = new BaseRvAdapter<Movie, MovieViewHolder>(R.layout.movie_item, MovieViewHolder.class, movies, this) {
            @Override
            protected void bindView(MovieViewHolder holder, Movie movie, int position) {
                holder.bind(movie, SearchResultsActivity.this);
            }
        };

        rvMovies.setAdapter(adapter);
    }


    @Override
    public void showLoading(boolean isShowLoading) {
        if (isShowLoading) {
            loadingProgress.setVisibility(View.VISIBLE);
            rvMovies.setVisibility(View.GONE);
            statusLayout.setVisibility(View.GONE);
        } else {
            loadingProgress.setVisibility(View.GONE);
            rvMovies.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void displayMovies(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showStatus(boolean isDataFound) {
        if (isDataFound) {
            statusLayout.setVisibility(View.GONE);
            rvMovies.setVisibility(View.VISIBLE);
        } else {
            statusLayout.setVisibility(View.VISIBLE);
            tvStatus.setText(getString(R.string.not_found));
            rvMovies.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemClick(int position) {
        Movie movie = movies.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_INTENT, movie);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
