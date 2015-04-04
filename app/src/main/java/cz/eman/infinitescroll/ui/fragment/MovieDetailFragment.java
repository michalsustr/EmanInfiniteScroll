package cz.eman.infinitescroll.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.ui.activity.MovieDetailActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        return rootView;
    }

    public void showMovie(Integer movieId) {
        Log.d("APP", "show movie id " + movieId);
    }
}