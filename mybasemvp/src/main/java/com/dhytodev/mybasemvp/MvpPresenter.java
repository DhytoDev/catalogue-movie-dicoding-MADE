package com.dhytodev.mybasemvp;

/**
 * Created by izadalab on 9/6/17.
 */

public interface MvpPresenter<V extends MvpView, I extends MvpInteractor> {

    void onAttach(V mvpView);

    void onDetach();

    V getMvpView();

    I getInteractor();
}
