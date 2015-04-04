package cz.eman.infinitescroll.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.ui.fragment.MovieDetailFragment;
import cz.eman.infinitescroll.ui.fragment.MovieInfiniteListFragment;


public class MainActivity extends ActionBarActivity {
    private MovieDetailFragment movieDetailFragment;
    private MovieInfiniteListFragment movieInfiniteListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
