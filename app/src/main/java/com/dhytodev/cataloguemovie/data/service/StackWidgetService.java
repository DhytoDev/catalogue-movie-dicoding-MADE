package com.dhytodev.cataloguemovie.data.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.dhytodev.cataloguemovie.ui.widget.FavoriteStackRemoteViewsFactory;

/**
 * Created by izadalab on 16/02/18.
 */

public class StackWidgetService extends RemoteViewsService  {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoriteStackRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
