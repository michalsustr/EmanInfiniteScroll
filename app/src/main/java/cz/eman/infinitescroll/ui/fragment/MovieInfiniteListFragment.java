package cz.eman.infinitescroll.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.model.RestClient;
import cz.eman.infinitescroll.model.entity.Movie;
import cz.eman.infinitescroll.model.entity.RestError;
import cz.eman.infinitescroll.model.rest.RestCallback;
import cz.eman.infinitescroll.model.service.MovieService;
import cz.eman.infinitescroll.ui.activity.MovieDetail;
import cz.eman.infinitescroll.ui.adapter.MovieAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MovieInfiniteListFragment extends ListFragment
        implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private int currentPage = 1;
    private RestClient restClient;
    private MovieService movieService;
    private MovieAdapter adapter;
    private Context context;
    private int threshold = 0;
    private View loadingView;
    private TextView descriptionView;

    public MovieInfiniteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_scrollview, container, false);
        View footerView = inflater.inflate(R.layout.view_loadingprogress, null);
        loadingView = footerView.findViewById(R.id.footer);

        View headerView = inflater.inflate(R.layout.view_information, null);
        descriptionView = (TextView) headerView.findViewById(R.id.header);
        descriptionView.setText(Html.fromHtml(getString(R.string.about_info)));

        context = container.getContext();

        adapter = new MovieAdapter(context);
        restClient = new RestClient();
        movieService = restClient.getMovieService();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().addFooterView(loadingView);
        getListView().addHeaderView(descriptionView);
        setListAdapter(adapter);
        getListView().setOnScrollListener(this);
        getListView().setOnItemClickListener(this);
        loadData(currentPage);
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
        showLoading();

        movieService.getMovies(currentPage, 5, new RestCallback<List<Movie>>() {
            @Override
            public void success(List<Movie> movies, Response response) {
                Log.d("APP", "Loaded movies " + movies);
                Log.d("APP", "Loaded " + movies.size());
                for (Movie m : movies) adapter.add(m);
                adapter.notifyDataSetChanged();

                hideLoading();
            }

            @Override
            public void failure(RestError error) {
                Log.d("APP", "Error "+error.getError());
                Toast.makeText(context,
                        getString(R.string.ERROR_LOAD_DATA), Toast.LENGTH_LONG)
                        .show();
                getListView().removeFooterView(loadingView);

                hideLoading();
            }
        });
    }

    // for such a simple thing we need locking ... omg
    private volatile boolean showsLoading = false;
    private synchronized void showLoading() {
        if(!showsLoading) {
            showsLoading = true;
            getListView().addFooterView(loadingView);
        }
    }
    private synchronized void hideLoading() {
        if(showsLoading) {
            showsLoading = false;
            getListView().removeFooterView(loadingView);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), MovieDetail.class);
        intent.putExtra(MovieDetail.EXTRA_MOVIE_ID, adapter.getItem(position).getId());
        startActivity(intent);
    }
}