package com.dhytodev.cataloguemovie.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dhytodev.cataloguemovie.BuildConfig;
import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by izadalab on 03/12/17.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder  {

    @BindView(R.id.iv_movie_poster)
    ImageView moviePoster ;
    @BindView(R.id.tv_movie_title)
    TextView movieTitle ;
    @BindView(R.id.tv_movie_overview)
    TextView movieOverview ;
    @BindView(R.id.tv_movie_release_date)
    TextView movieReleaseDate ;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Movie movie, Context context) {
        Glide.with(context)
                .load(BuildConfig.API_POSTER_PATH + movie.getPosterPath())
                .into(moviePoster);
        movieTitle.setText(movie.getTitle());
        movieOverview.setText(movie.getOverview());
        movieReleaseDate.setText(movie.getReleaseDate());
    }
}
