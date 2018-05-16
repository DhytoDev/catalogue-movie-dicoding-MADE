package com.dhytodev.cataloguemovie.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dhytodev.cataloguemovie.BuildConfig;
import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract;
import com.dhytodev.cataloguemovie.data.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.*;

/**
 * Created by izadalab on 16/02/18.
 */

public class FavoriteStackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context context;
    private int mAppWidgetId;
    private Cursor cursor;

    public FavoriteStackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                cursor == null || !cursor.moveToPosition(position)) {
            return null;
        }

        Movie movie = getItem(position);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.movie_grid_item);

        Bitmap bmp = null;
        try {

            bmp = Glide.with(context)
                    .load(BuildConfig.API_POSTER_PATH + movie.getPosterPath())
                    .asBitmap()
                    //.error(new ColorDrawable(context.getResources().getColor(R.color.colorPrimary)))
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        Log.e(TAG, "getViewAt: " + movie.getTitle());

        rv.setImageViewBitmap(R.id.movie_poster, bmp);
        rv.setTextViewText(R.id.tv_movie_title, movie.getTitle());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    private Movie getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new Movie(cursor);
    }
}
