package com.dhytodev.mybasemvp;


/**
 * Created by izadalab on 9/6/17.
 */

public class BaseInteractor<T> implements MvpInteractor<T> {

    private T service;

    public BaseInteractor(T service) {
        this.service = service;
    }

    @Override
    public T getService() {
        return service;
    }
}
