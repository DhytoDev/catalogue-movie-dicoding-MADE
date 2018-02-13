package com.izadalab.myfav.ui.favorite;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dhytodev.mybasemvp.BaseActivity;
import com.dhytodev.mybasemvp.BaseRvAdapter;
import com.dhytodev.mybasemvp.listener.RecyclerViewItemClickListener;
import com.izadalab.myfav.R;
import com.izadalab.myfav.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class FavoriteListingActivity extends BaseActivity implements RecyclerViewItemClickListener, FavoriteView {

    @BindView(R.id.rv_list_movies)
    RecyclerView rvListMovies;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;

    private BaseRvAdapter<Movie, MovieViewHolder> adapter ;
    private List<Movie> movies = new ArrayList<>();
    private FavoritePresenter presenter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnbinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        final FavoriteInteractor interactor = new FavoriteInteractorImpl(this);
        presenter = new FavoritePresenter(interactor, new CompositeDisposable());
        presenter.onAttach(this);
        presenter.fetchMoviesData();

        rvListMovies.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvListMovies.setLayoutManager(layoutManager);

        adapter = new BaseRvAdapter<Movie, MovieViewHolder>(R.layout.movie_item, MovieViewHolder.class, movies, this) {
            @Override
            protected void bindView(MovieViewHolder holder, Movie movie, int position) {
                holder.bind(movie, FavoriteListingActivity.this);
            }
        };

        rvListMovies.setAdapter(adapter);

        refresh.setOnRefreshListener(() -> {
            refresh.setRefreshing(false);
            presenter.fetchMoviesData();
        });
    }

    @Override
    public void onItemClick(int position) {
        showMessage(movies.get(position).getTitle());
    }

    @Override
    public void showLoading(boolean isShowLoading) {
        tvError.setVisibility(View.GONE);
        if (isShowLoading) {
            rvListMovies.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
        } else {
            loadingProgress.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayMovies(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        rvListMovies.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }
}
