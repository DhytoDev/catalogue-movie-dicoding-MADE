package com.dhytodev.cataloguemovie.ui.main.nav_menu;

import android.util.Log;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.mybasemvp.BasePresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by izadalab on 03/12/17.
 */

public class MainPresenter<V extends MainView, I extends MainInteractor> extends BasePresenter<V, I> {

    public MainPresenter(I mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }

    public void fetchMovies(int movie) {
        getMvpView().showLoading(true);

        Observable<List<Movie>> observableMovies;

        if (movie == 0) {
            observableMovies = getInteractor().fetchNowPlayingMovies();
        } else if (movie == 1) {
            observableMovies = getInteractor().fetchUpComingMovies();
        } else {
            observableMovies = getInteractor().fetchFavoriteMovies();
        }

        getCompositeDisposable().add(observableMovies.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    getMvpView().showLoading(false);
                    getMvpView().displayMovies(movies);
                }, throwable -> {
                    getMvpView().showLoading(false);
                    getMvpView().onError(throwable.getLocalizedMessage());
                    Log.e("error", throwable.getLocalizedMessage());
                    //getMvpView().onError("hello");
                }));
    }
}
