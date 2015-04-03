package cz.eman.infinitescroll.model.service;

import java.util.List;

import cz.eman.infinitescroll.model.entity.Movie;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface MovieService
{
    @GET("/lists/movies/upcoming.json")
    void getMovies(@Query("page") int page, @Query("page_limit") int pageLimit, Callback<List<Movie>> cb);
}
