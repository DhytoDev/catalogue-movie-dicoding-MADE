package com.dhytodev.cataloguemovie.ui.detail;

import com.dhytodev.cataloguemovie.data.model.Trailer;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.mybasemvp.BaseInteractor;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by izadalab on 03/12/17.
 */

public class DetailInteractorImpl extends BaseInteractor<MovieService> implements DetailInteractor {



    public DetailInteractorImpl(MovieService service) {
        super(service);
    }

    @Override
    public Observable<List<Trailer>> getMovieTrailers(String id) {
        return getService().getMovieTrailers(id).flatMap(trailerResponse -> Observable.just(trailerResponse.getResults()));
    }


}
