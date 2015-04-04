package cz.eman.infinitescroll.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.ui.activity.MovieDetail;

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

        Log.d("APP", "movie id " + getArguments().getInt(MovieDetail.EXTRA_MOVIE_ID));

        return rootView;
    }
}