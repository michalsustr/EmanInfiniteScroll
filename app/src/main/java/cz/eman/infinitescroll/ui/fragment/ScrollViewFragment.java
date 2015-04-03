package cz.eman.infinitescroll.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.List;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.model.RestClient;
import cz.eman.infinitescroll.model.entity.Movie;
import cz.eman.infinitescroll.model.service.MovieService;
import cz.eman.infinitescroll.ui.adapter.MovieAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class ScrollViewFragment extends ListFragment implements AbsListView.OnScrollListener{
    private int currentPage = 1;
    private RestClient restClient;
    private MovieService movieService;
    private MovieAdapter adapter;
    private Context context;
    private int threshold = 0;

    public ScrollViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_scrollview, container, false);

        context = container.getContext();

        adapter = new MovieAdapter(context);
        setListAdapter(adapter);

        restClient = new RestClient();
        movieService = restClient.getMovieService();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //getListView().setOnScrollListener(this);
        loadData(1);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            if (getListView().getLastVisiblePosition() >= getListView().getCount() - 1 - threshold) {
                currentPage++;
                //load more list items:
                loadData(currentPage);
            }
        }
    }

    private void loadData(int currentPage) {
        movieService.getMovies(currentPage, 5, new Callback<List<Movie>>() {
            @Override
            public void success(List<Movie> movies, Response response) {
                Log.d("APP", "Loaded " + movies.size());
                for (Movie m : movies) adapter.add(m);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("APP", "Error "+error);
                String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                Log.d("APP", "HTTP response body "+json);
                Toast.makeText(context,
                        getString(R.string.ERROR_LOAD_DATA), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}
}
