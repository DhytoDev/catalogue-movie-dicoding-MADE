package com.izadalab.myfav.ui.favorite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.izadalab.myfav.BuildConfig;
import com.izadalab.myfav.R;
import com.izadalab.myfav.data.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by izadalab on 13/02/18.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {
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
                .placeholder(R.drawable.ic_placeholder)
                .into(moviePoster);
        movieTitle.setText(movie.getTitle());
        movieOverview.setText(movie.getOverview());
        movieReleaseDate.setText(String.format(context.getString(R.string.release_date), movie.getReleaseDate()));
    }
}
