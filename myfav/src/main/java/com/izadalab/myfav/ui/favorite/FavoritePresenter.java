package com.izadalab.myfav.ui.favorite;

import com.dhytodev.mybasemvp.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by izadalab on 13/02/18.
 */

public class FavoritePresenter extends BasePresenter<FavoriteView, FavoriteInteractor> {

    public FavoritePresenter(FavoriteInteractor mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }

    public void fetchMoviesData() {
        getMvpView().showLoading(true);
        getCompositeDisposable().add(getInteractor().fetchMovieFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    getMvpView().showLoading(false);
                    getMvpView().displayMovies(movies);
                }, throwable -> {
                    getMvpView().showLoading(false);
                    getMvpView().onError(throwable.getLocalizedMessage());
                }));
    }
}
