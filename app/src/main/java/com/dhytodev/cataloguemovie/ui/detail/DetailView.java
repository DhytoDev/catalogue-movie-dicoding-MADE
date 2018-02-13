package com.dhytodev.cataloguemovie.ui.detail;

import android.view.View;

import com.dhytodev.cataloguemovie.data.model.Trailer;
import com.dhytodev.mybasemvp.MvpView;

import java.util.List;

/**
 * Created by izadalab on 03/12/17.
 */

public interface DetailView extends MvpView {
    void displayTrailers(List<Trailer> trailers);
    void setFavoriteFabIcon(boolean isFavorited);
}
