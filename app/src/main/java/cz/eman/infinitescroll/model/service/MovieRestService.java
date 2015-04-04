package cz.eman.infinitescroll.model.service;

import cz.eman.infinitescroll.model.rest.API;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface MovieRestService
{
    @GET("/lists/movies/upcoming.json")
    void getMovies(@Query("page") int page, @Query("page_limit") int pageLimit, Callback<API> cb);
}
