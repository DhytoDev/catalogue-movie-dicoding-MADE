package com.dhytodev.cataloguemovie.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.provider.BaseColumns._ID;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.CONTENT_AUTHORITY;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.CONTENT_URI;
import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.TABLE_MOVIES;

/**
 * Created by izadalab on 12/02/18.
 */

public class DatabaseMovieProvider extends ContentProvider {

    private static final int MOVIES = 100;
    private static final int MOVIES_WITH_ID = 101;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private DatabaseMovieHelper dbHelper;

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, TABLE_MOVIES, MOVIES);
        uriMatcher.addURI(CONTENT_AUTHORITY, TABLE_MOVIES + "/#", MOVIES_WITH_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseMovieHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = uriMatcher.match(uri);

        Cursor cursor;

        switch (match) {
            case MOVIES:
            case MOVIES_WITH_ID:
                if (match == MOVIES_WITH_ID) {
                    final long id = ContentUris.parseId(uri);
                    selection = String.format("%s = ?", _ID);
                    selectionArgs = new String[]{String.valueOf(id)};
                }

                cursor = db.query(TABLE_MOVIES, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri insertUri;

        switch (match) {
            case MOVIES:
                long id = db.insert(TABLE_MOVIES, null, contentValues);
                if (id > 0) {
                    insertUri = ContentUris.withAppendedId(CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return insertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                selection = (selection == null) ? "1" : selection;
                break;
            case MOVIES_WITH_ID:
                long id = ContentUris.parseId(uri);
                selection = String.format("%s = ?", _ID);
                selectionArgs = new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Illegal delete URI");
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delete = db.delete(TABLE_MOVIES, selection, selectionArgs);

        if (delete > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        int update;

        switch (match) {
            case MOVIES:
                update = db.update(TABLE_MOVIES, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return update;
    }
}
