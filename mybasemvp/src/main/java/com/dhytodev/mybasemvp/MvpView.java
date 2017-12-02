package com.dhytodev.mybasemvp;

import android.support.annotation.StringRes;

/**
 * Created by izadalab on 9/6/17.
 */

public interface MvpView {

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);
}
