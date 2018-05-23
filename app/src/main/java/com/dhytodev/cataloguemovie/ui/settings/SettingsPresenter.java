package com.dhytodev.cataloguemovie.ui.settings;

import android.util.Log;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.mybasemvp.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SettingsPresenter<V extends SettingsView, I extends SettingsInteractor> extends BasePresenter<V, I> {

    public SettingsPresenter(I mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }


    public void setRepeatingAlarm() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        Log.e("currentDate", currentDate);

        getCompositeDisposable().add(getInteractor().fetchMovieTodayRelease()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(movies -> {
                    for (Movie movie : movies) {
                        if (movie.getReleaseDate().equals(currentDate)) {
                            getMvpView().setAlarm(movie);
                        }
                    }
                }));
    }

    public void cancelAlarm() {
        getMvpView().cancelAlarm();
    }
}
