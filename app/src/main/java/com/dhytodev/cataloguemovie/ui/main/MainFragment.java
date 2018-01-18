package com.dhytodev.cataloguemovie.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.cataloguemovie.ui.detail.DetailActivity;
import com.dhytodev.mybasemvp.BaseFragment;
import com.dhytodev.mybasemvp.BaseRvAdapter;
import com.dhytodev.mybasemvp.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by izadalab on 19/01/18.
 */

public class MainFragment extends BaseFragment implements MainView, RecyclerViewItemClickListener {

    @BindView(R.id.rv_list_movies)
    RecyclerView rvMovies;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.refresh)
    protected SwipeRefreshLayout refresh;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;

    protected BaseRvAdapter<Movie, MovieViewHolder> adapter;
    protected List<Movie> movies = new ArrayList<>();
    protected MainPresenter<MainView, MainInteractor> presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movies, container, false);
        setUnbinder(ButterKnife.bind(this, view));

        return view;
    }

    @Override
    protected void setUp(View view) {
        MainInteractor interactor = new MainInteractorImpl(MovieService.ServiceGenerator.instance());
        presenter = new MainPresenter<>(interactor, new CompositeDisposable());
        presenter.onAttach(this);

        rvMovies.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvMovies.setLayoutManager(layoutManager);

        adapter = new BaseRvAdapter<Movie, MovieViewHolder>(R.layout.movie_item, MovieViewHolder.class, movies, this) {
            @Override
            protected void bindView(MovieViewHolder holder, Movie movie, int position) {
                holder.bind(movie, getContext());
            }
        };

        rvMovies.setAdapter(adapter);
    }

    @Override
    public void showLoading(boolean isShowLoading) {
        tvError.setVisibility(View.GONE);
        if (isShowLoading) {
            rvMovies.setVisibility(View.VISIBLE);
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
        rvMovies.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Movie movie = movies.get(position);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_INTENT, movie);
        startActivity(intent);
    }
}
