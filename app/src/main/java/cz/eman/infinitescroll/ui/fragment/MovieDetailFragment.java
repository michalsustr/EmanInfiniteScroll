package cz.eman.infinitescroll.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.model.entity.Movie;
import cz.eman.infinitescroll.model.service.MovieDbService;
import cz.eman.infinitescroll.ui.activity.MovieDetailActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    private TextView titleView;
    private TextView yearView;
    private TextView synopsisView;

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        titleView = (TextView) rootView.findViewById(R.id.title);
        yearView = (TextView) rootView.findViewById(R.id.year);
        synopsisView = (TextView) rootView.findViewById(R.id.synopsis);

        return rootView;
    }

    public void showMovie(Integer movieId) {
        Log.d("APP", "show movie id " + movieId);
        Movie movie = MovieDbService.getMovieById(movieId);
        titleView.setText(movie.getTitle());
        yearView.setText(""+movie.getYear());
        synopsisView.setText(movie.getSynopsis());
    }
}