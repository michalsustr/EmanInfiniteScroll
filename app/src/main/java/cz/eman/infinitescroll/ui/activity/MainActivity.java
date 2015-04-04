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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_offline) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
