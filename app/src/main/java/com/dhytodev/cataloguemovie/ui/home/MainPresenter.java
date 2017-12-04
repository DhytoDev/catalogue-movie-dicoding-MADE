package com.dhytodev.cataloguemovie.ui.home;

import android.util.Log;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.mybasemvp.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by izadalab on 03/12/17.
 */

public class MainPresenter<V extends MainView,I extends MainInteractor> extends BasePresenter<V, I> {

    public MainPresenter(I mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }

    public void getSearchMovies(String movieName) {
        getMvpView().showLoading(true);

        getCompositeDisposable()
                .add(getInteractor().fetchSearchMovies(movieName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    getMvpView().showLoading(false);

                    if(movies != null && movies.size() > 0) {
                        getMvpView().displayMovies(movies);
                        getMvpView().showStatus(true);
                    } else {
                        getMvpView().showStatus(false);
                    }
                }, throwable -> {
                    getMvpView().showLoading(false);
                    getMvpView().onError(throwable.getLocalizedMessage());
                }));
    }
}
