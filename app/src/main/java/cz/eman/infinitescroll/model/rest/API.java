package cz.eman.infinitescroll.model.rest;

import java.util.List;

import cz.eman.infinitescroll.model.entity.Movie;

public class API {
    private Integer total;
    private List<Movie> movies;
    //private RestLink links;


    public Integer getTotal() {
        return total;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
