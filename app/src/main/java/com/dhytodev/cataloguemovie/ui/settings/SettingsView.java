package com.dhytodev.cataloguemovie.ui.settings;

import android.content.Context;

import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.mybasemvp.MvpView;

import java.util.List;

public interface SettingsView extends MvpView {
    void setAlarm(Movie movie);

    void cancelAlarm();
}
