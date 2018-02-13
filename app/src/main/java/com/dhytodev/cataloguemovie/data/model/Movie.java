package com.dhytodev.cataloguemovie.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract;
import com.google.gson.annotations.SerializedName;

import static com.dhytodev.cataloguemovie.data.local.DatabaseMovieContract.MovieColumns.*;

/**
 * Created by izadalab on 7/8/17.
 */

public class Movie implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("vote_average")
    private float voteAverage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeFloat(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.voteCount);
        dest.writeFloat(this.voteAverage);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.popularity = in.readFloat();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.voteCount = in.readInt();
        this.voteAverage = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_ID));
        this.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_TITLE));
        this.popularity = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_POPULARITY));
        this.posterPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_POSTER_PATH));
        this.backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_BACKDROP_PATH));
        this.overview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_OVERVIEW));
        this.releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_RELEASE_DATE));
        this.voteCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_VOTE_COUNT));
        this.voteAverage = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_VOTE_AVERAGE));
    }
}
