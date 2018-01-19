package com.dhytodev.cataloguemovie.data.network;

import com.dhytodev.cataloguemovie.BuildConfig;
import com.dhytodev.cataloguemovie.data.model.MoviesResponse;
import com.dhytodev.cataloguemovie.data.model.TrailerResponse;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by izadalab on 30/11/17.
 */

public interface MovieService {

    @GET("search/movie")
    Observable<MoviesResponse> searchMovies(@Query("query") String movieName) ;

    @GET("movie/{id}/videos")
    Observable<TrailerResponse> getMovieTrailers(@Path("id") String movieId);

    @GET("movie/now_playing")
    Observable<MoviesResponse> getNowPlayingMovies();

    @GET("movie/upcoming")
    Observable<MoviesResponse> getUpcomingMovies();

    class ServiceGenerator {
        public static MovieService instance() {
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            final ApiKeyInterceptor apiKeyInterceptor = new ApiKeyInterceptor();

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(apiKeyInterceptor)
                    .build();
            final Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .build();

            return retrofit.create(MovieService.class);
        }
    }

}
