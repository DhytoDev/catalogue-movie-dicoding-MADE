package com.dhytodev.cataloguemovie.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by izadalab on 9/12/17.
 */

public class TrailerResponse {
    @SerializedName("results")
    private List<Trailer> results ;

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
