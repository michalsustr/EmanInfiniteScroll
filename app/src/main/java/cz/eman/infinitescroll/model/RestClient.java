package cz.eman.infinitescroll.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.eman.infinitescroll.model.rest.MovieTypeAdapterFactory;
import cz.eman.infinitescroll.model.service.MovieService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {
    private static final String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0";
    private static final String API_KEY = "4wfs9wmcezvpp48ngwnrvhvj";
    private static final String COUNTRY = "us";

    private RestAdapter mRestAdapter;
    private MovieService mMovieService;

    public RestClient() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new MovieTypeAdapterFactory())
                .create();

        mRestAdapter = new RestAdapter.Builder()
            .setEndpoint(BASE_URL)
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addEncodedQueryParam("apikey", API_KEY);
                    request.addEncodedQueryParam("country", COUNTRY);
                }
            })
            .setConverter(new GsonConverter(gson))
            .build();


    }

    public MovieService getMovieService() {
        if(mMovieService == null) {
            mMovieService = mRestAdapter.create(MovieService.class);
        }
        return mMovieService;
    }
}
