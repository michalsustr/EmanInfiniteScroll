package cz.eman.infinitescroll.ui.activity;



import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.ui.fragment.MovieDetailFragment;


public class MovieDetailActivity extends ActionBarActivity {

    public static final String EXTRA_MOVIE_ID = "movieId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Show movie invoked by intent
        MovieDetailFragment detailFragment = (MovieDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailFragment);
        detailFragment.showMovie(getIntent().getExtras().getInt(EXTRA_MOVIE_ID));
    }
}
