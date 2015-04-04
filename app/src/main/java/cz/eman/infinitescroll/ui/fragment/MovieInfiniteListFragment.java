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
import android.widget.TextView;
import android.widget.Toast;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.model.RestClient;
import cz.eman.infinitescroll.model.entity.Movie;
import cz.eman.infinitescroll.model.entity.RestError;
import cz.eman.infinitescroll.model.rest.API;
import cz.eman.infinitescroll.model.rest.RestCallback;
import cz.eman.infinitescroll.model.service.MovieService;
import cz.eman.infinitescroll.ui.activity.MovieDetail;
import cz.eman.infinitescroll.ui.adapter.MovieAdapter;
import retrofit.client.Response;

public class MovieInfiniteListFragment extends ListFragment
        implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private int currentPage = 1;
    private int totalRecords;

    private RestClient restClient;
    private MovieService movieService;
    private MovieAdapter adapter;
    private Context context;
    private int threshold = 0;
    private View loadingView;
    private TextView descriptionView;
    private TextView nomoreDataView;
    private TextView loadDataView;


    public MovieInfiniteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_scrollview, container, false);

        loadingView = inflater.inflate(R.layout.view_loadingprogress, null)
                .findViewById(R.id.footer);
        loadDataView = (TextView) inflater.inflate(R.layout.view_loaddata, null)
                .findViewById(R.id.footer);
        loadDataView.setOnClickListener(onLoadButtonClick);
        descriptionView = (TextView) inflater.inflate(R.layout.view_information, null)
                .findViewById(R.id.header);
        descriptionView.setText(Html.fromHtml(getString(R.string.about_info)));
        nomoreDataView = (TextView) inflater.inflate(R.layout.view_nomoredata, null)
                .findViewById(R.id.header);;

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

    private View.OnClickListener onLoadButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadNextPage();
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            if (getListView().getLastVisiblePosition() >= getListView().getCount() - 1 - threshold) {
                loadNextPage();
            }
        }
    }

    private void loadNextPage() {
        currentPage++;
        loadData(currentPage);
    }

    private void loadData(int currentPage) {
        showLoading();

        movieService.getMovies(currentPage, 3, new RestCallback<API>() {
            @Override
            public void success(API api, Response response) {
                Log.d("APP", "Loaded movies " + api.getMovies());
                Log.d("APP", "Loaded " + api.getMovies().size());
                for (Movie m : api.getMovies()) adapter.add(m);
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
            getListView().removeFooterView(loadDataView);
        }
    }
    private synchronized void hideLoading() {
        if(showsLoading) {
            showsLoading = false;
            getListView().removeFooterView(loadingView);
            getListView().addFooterView(loadDataView);
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
