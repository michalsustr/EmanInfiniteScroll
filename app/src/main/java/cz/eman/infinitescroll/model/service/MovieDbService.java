package cz.eman.infinitescroll.model.service;

import com.activeandroid.query.Select;

import java.util.List;

import cz.eman.infinitescroll.model.entity.Movie;

/**
 * Service to retrieve data saved in database
 */
public class MovieDbService {

    public static Movie getMovieById(Integer sid) {
        return new Select().from(Movie.class).where("sid = ?", sid).executeSingle();
    }

    public static List<Movie> getMovies() {
        return new Select().from(Movie.class).execute();
    }
}
