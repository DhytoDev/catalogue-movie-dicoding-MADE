package com.izadalab.myfav.ui.favorite;

import android.content.Context;
import android.database.Cursor;

import com.dhytodev.mybasemvp.BaseInteractor;
import com.izadalab.myfav.data.DatabaseMovieContract;
import com.izadalab.myfav.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.izadalab.myfav.data.DatabaseMovieContract.CONTENT_URI;

/**
 * Created by izadalab on 13/02/18.
 */

public class FavoriteInteractorImpl extends BaseInteractor implements FavoriteInteractor {

    private Context context ;

    public FavoriteInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<Movie>> fetchMovieFavorites() {
        return Observable.create(e -> {
            List<Movie> movies = new ArrayList<>();
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    movies.add(new Movie(cursor));
                } while (cursor.moveToNext());
            }

            cursor.close();
            e.onNext(movies);
        });
    }
}
