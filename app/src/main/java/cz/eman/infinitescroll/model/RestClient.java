package cz.eman.infinitescroll.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.eman.infinitescroll.model.service.MovieRestService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {
    private static final String BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0";
    private static final String API_KEY = "4wfs9wmcezvpp48ngwnrvhvj";
    private static final String COUNTRY = "us";

    private RestAdapter mRestAdapter;
    private MovieRestService mMovieRestService;

    public RestClient() {
        Gson gson = new GsonBuilder()
                .create();

        mRestAdapter = new RestAdapter.Builder()
            .setEndpoint(BASE_URL)
            .setLogLevel(RestAdapter.LogLevel.FULL)
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

    public MovieRestService getMovieService() {
        if(mMovieRestService == null) {
            mMovieRestService = mRestAdapter.create(MovieRestService.class);
        }
        return mMovieRestService;
    }
}
