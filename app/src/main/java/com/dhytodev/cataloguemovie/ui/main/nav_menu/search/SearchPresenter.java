package com.dhytodev.cataloguemovie.ui.main.nav_menu.search;

import com.dhytodev.mybasemvp.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by izadalab on 19/01/18.
 */

public class SearchPresenter extends BasePresenter<SearchView, SearchInteractor> {

    public SearchPresenter(SearchInteractor mvpInteractor, CompositeDisposable compositeDisposable) {
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

                            if (movies != null && movies.size() > 0) {
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
