package com.dhytodev.cataloguemovie.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by izadalab on 7/8/17.
 */

public class MoviesResponse {
    @SerializedName("results")
    private List<Movie> results ;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
