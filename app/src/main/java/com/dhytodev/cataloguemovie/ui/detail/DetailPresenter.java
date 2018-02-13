package com.dhytodev.cataloguemovie.ui.detail;

import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.mybasemvp.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by izadalab on 03/12/17.
 */

public class DetailPresenter extends BasePresenter<DetailView, DetailInteractor> {

    public DetailPresenter(DetailInteractor mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }

    public void fetchTrailers(String id) {
        getCompositeDisposable()
                .add(getInteractor().getMovieTrailers(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(trailers -> {
                            if (trailers != null && trailers.size() > 0) {
                                getMvpView().displayTrailers(trailers);
                            }
                        }, throwable -> {
                            getMvpView().onError(throwable.getLocalizedMessage());
                        }));
    }

    public void isMovieFavorited(Movie movie) {
        getCompositeDisposable().add(getInteractor().isMovieFavorited(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> getMvpView().setFavoriteFabIcon(aBoolean),
                        throwable -> getMvpView().onError(throwable.getLocalizedMessage())));
    }

    public void addMovieToFavoriteList(Movie movie) {
        getCompositeDisposable().add(getInteractor().saveMovie(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getMvpView().onError(R.string.movie_added_to_favorite),
                        throwable -> getMvpView().onError(throwable.getLocalizedMessage())));
    }

    public void deleteMovieFromFavoriteList(Movie movie) {
        getCompositeDisposable().add(getInteractor().deleteMovie(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getMvpView().onError(R.string.movie_removed_from_favorite),
                        throwable -> getMvpView().onError(throwable.getLocalizedMessage())));
    }

}
