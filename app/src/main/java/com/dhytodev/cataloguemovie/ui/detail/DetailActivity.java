package com.dhytodev.cataloguemovie.ui.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.dhytodev.cataloguemovie.BuildConfig;
import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.model.Trailer;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class DetailActivity extends BaseActivity implements DetailView, View.OnClickListener {

    public static final String MOVIE_INTENT = "movie.intent";

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.movie_backdrop)
    ImageView movieBackdrop;
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.movie_rating)
    TextView movieRating;
    @BindView(R.id.movie_vote_count)
    TextView movieVoteCount;
    @BindView(R.id.movie_overview)
    TextView movieOverview;
    @BindView(R.id.movie_background)
    View movieBackground;
    @BindView(R.id.trailers_label)
    TextView trailersLabel;
    @BindView(R.id.trailers)
    LinearLayout trailersLayout;
    @BindView(R.id.trailers_container)
    HorizontalScrollView trailersContainer;
    @BindView(R.id.movie_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.scrolling_container)
    NestedScrollView scrollingContainer;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.movie_summary)
    TextView movieSummary;

    DetailInteractor detailInteractor;
    DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setUnbinder(ButterKnife.bind(this));
        setUp();

        if (savedInstanceState == null) {
            Movie movie = getIntent().getParcelableExtra(MOVIE_INTENT);
            if (movie != null) {
                displayDetails(movie);
                detailPresenter.onAttach(this);
                detailPresenter.fetchTrailers(String.valueOf(movie.getId()));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.onDetach();
    }

    @Override
    protected void setUp() {
        detailInteractor = new DetailInteractorImpl(MovieService.ServiceGenerator.instance());
        detailPresenter = new DetailPresenter(detailInteractor, new CompositeDisposable());

        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary));
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {

        }
    }

    private void displayDetails(Movie movie) {
        if (movie.getOverview().isEmpty()) {
            movieOverview.setVisibility(View.GONE);
            movieSummary.setVisibility(View.GONE);
        } else {
            movieOverview.setVisibility(View.VISIBLE);
            movieSummary.setVisibility(View.VISIBLE);
            Glide.with(this).load(BuildConfig.API_BACKDROP_PATH + movie.getBackdropPath())
                    .placeholder(R.drawable.ic_placeholder).centerCrop().into(movieBackdrop);
            movieOverview.setText(movie.getOverview());
            movieRating.setText(String.format(getString(R.string.rating), String.valueOf(movie.getVoteAverage())));
            movieTitle.setText(movie.getTitle());
            movieVoteCount.setText(String.format(getString(R.string.vote_count), String.valueOf(movie.getVoteCount())));
            movieReleaseDate.setText(String.format(getString(R.string.release_date), String.valueOf(movie.getReleaseDate())));
            collapsingToolbar.setTitle(movie.getTitle());

            Glide
                    .with(this)
                    .load(BuildConfig.API_POSTER_PATH + movie.getPosterPath()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.ic_placeholder)
                    .centerCrop()
                    .into(new BitmapImageViewTarget(moviePoster) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(resource, glideAnimation);

                            Palette.from(resource).generate(palette -> {
                                movieBackground.setBackgroundColor(palette
                                        .getVibrantColor(getResources().getColor(R.color.black_translucent_60)));
                            });
                        }
                    });
        }
    }

    @Override
    public void displayTrailers(List<Trailer> trailers) {
        if (trailers.isEmpty()) {
            trailersLabel.setVisibility(View.GONE);
            trailersContainer.setVisibility(View.GONE);
            trailersLayout.setVisibility(View.GONE);
        } else {
            trailersLabel.setVisibility(View.VISIBLE);
            trailersContainer.setVisibility(View.VISIBLE);
            trailersLayout.setVisibility(View.VISIBLE);

            trailersLayout.removeAllViews();

            for (Trailer trailer : trailers) {
                View thumbContainer = LayoutInflater.from(this).inflate(R.layout.thumbnail, trailersLayout, false);
                ImageView thumbView = ButterKnife.findById(thumbContainer, R.id.video_thumb);
                thumbView.setTag(trailer.getKey());
                thumbView.requestLayout();
                thumbView.setOnClickListener(this);
                Picasso.with(this)
                        .load(trailer.getImageVideoUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_placeholder)
                        .resizeDimen(R.dimen.video_width, R.dimen.video_height)
                        .into(thumbView);
                trailersLayout.addView(thumbContainer);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String key = view.getTag().toString();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
