package com.dhytodev.mybasemvp;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by izadalab on 9/6/17.
 */

public class BasePresenter <V extends MvpView, I extends MvpInteractor> implements MvpPresenter<V, I> {

    private V mvpView ;
    private I mvpInteractor ;

    private CompositeDisposable compositeDisposable ;

    public BasePresenter(I mvpInteractor, CompositeDisposable compositeDisposable) {
        this.mvpInteractor = mvpInteractor;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView ;
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        mvpView = null ;
        mvpInteractor =null ;
    }

    @Override
    public V getMvpView() {
        return mvpView;
    }

    @Override
    public I getInteractor() {
        return mvpInteractor;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
