package com.dhytodev.cataloguemovie.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.BaseActivity;
import com.dhytodev.mybasemvp.BaseRvAdapter;
import com.dhytodev.mybasemvp.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity implements MainView, RecyclerViewItemClickListener{

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies ;
    @BindView(R.id.edt_search_movie)
    EditText etSearchMovie ;
    @BindView(R.id.btn_search)
    Button btnSearch ;

    private MainInteractor mainInteractor ;
    private MainPresenter<MainView, MainInteractor> mainPresenter ;

    private List<Movie> movies = new ArrayList<>();
    private BaseRvAdapter<Movie, MovieViewHolder> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnbinder(ButterKnife.bind(this));
        setUp();

        mainInteractor = new MainInteractorImpl(MovieService.ServiceGenerator.instance());
        mainPresenter = new MainPresenter<>(mainInteractor, new CompositeDisposable());
        mainPresenter.onAttach(this);

        btnSearch.setOnClickListener(view -> {
            mainPresenter.getSearchMovies(etSearchMovie.getText().toString());
        });

    }

    @Override
    protected void setUp() {
        rvMovies.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
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

    }

    @Override
    public void displayTvShows(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClicked(int position) {
        Movie movie = movies.get(position);
        Toast.makeText(this, movie.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
