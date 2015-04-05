package cz.eman.infinitescroll.model.service;

import cz.eman.infinitescroll.model.entity.API;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Service to retrieve data over network
 */
public interface MovieRestService
{
    @GET("/lists/movies/upcoming.json")
    void getMovies(@Query("page") int page, @Query("page_limit") int pageLimit, Callback<API> cb);
}
