package com.dhytodev.cataloguemovie.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.cataloguemovie.ui.detail.DetailActivity;
import com.dhytodev.cataloguemovie.ui.main.MainInteractor;
import com.dhytodev.cataloguemovie.ui.main.MainInteractorImpl;
import com.dhytodev.cataloguemovie.ui.main.MainPresenter;
import com.dhytodev.cataloguemovie.ui.main.MainView;
import com.dhytodev.cataloguemovie.ui.main.MovieViewHolder;
import com.dhytodev.mybasemvp.BaseActivity;
import com.dhytodev.mybasemvp.BaseRvAdapter;
import com.dhytodev.mybasemvp.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity implements MainView, RecyclerViewItemClickListener {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.edt_search_movie)
    EditText etSearchMovie;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.status_layout)
    LinearLayout statusLayout;

    private MainInteractor mainInteractor;
    private MainPresenter<MainView, MainInteractor> mainPresenter;

    private List<Movie> movies = new ArrayList<>();
    private BaseRvAdapter<Movie, MovieViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnbinder(ButterKnife.bind(this));
        setUp();

        mainPresenter.onAttach(this);

        btnSearch.setOnClickListener(view -> {
            String query = etSearchMovie.getText().toString();

            if (TextUtils.isEmpty(query)) {
                etSearchMovie.setError(getString(R.string.text_empty));
            } else {
                mainPresenter.getSearchMovies(etSearchMovie.getText().toString());
            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDetach();
    }

    @Override
    protected void setUp() {

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(getString(R.string.app_name));
        } else {
            Log.e("toolbar", "toolbar is null !");
        }

        mainInteractor = new MainInteractorImpl(MovieService.ServiceGenerator.instance());
        mainPresenter = new MainPresenter<>(mainInteractor, new CompositeDisposable());

        rvMovies.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMovies.setLayoutManager(layoutManager);

        adapter = new BaseRvAdapter<Movie, MovieViewHolder>(R.layout.movie_item, MovieViewHolder.class, movies, this) {
            @Override
            protected void bindView(MovieViewHolder holder, Movie movie, int position) {
                holder.bind(movie, MainActivity.this);
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

   /* @Override
    public void showStatus(boolean isDataFound) {
        if (isDataFound) {
            statusLayout.setVisibility(View.GONE);
            rvMovies.setVisibility(View.VISIBLE);
        } else {
            statusLayout.setVisibility(View.VISIBLE);
            tvStatus.setText(getString(R.string.not_found));
            rvMovies.setVisibility(View.GONE);
        }
    }*/


    @Override
    public void onItemClick(int position) {
        Movie movie = movies.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_INTENT, movie);
        startActivity(intent);
    }
}
