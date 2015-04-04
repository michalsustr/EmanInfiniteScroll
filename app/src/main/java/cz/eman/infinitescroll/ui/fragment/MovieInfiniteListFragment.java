package cz.eman.infinitescroll.ui.fragment;

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

import java.util.List;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.model.RestClient;
import cz.eman.infinitescroll.model.entity.AbridgedCast;
import cz.eman.infinitescroll.model.entity.Movie;
import cz.eman.infinitescroll.model.rest.RestError;
import cz.eman.infinitescroll.model.entity.API;
import cz.eman.infinitescroll.model.rest.RestCallback;
import cz.eman.infinitescroll.model.service.MovieDbService;
import cz.eman.infinitescroll.model.service.MovieRestService;
import cz.eman.infinitescroll.ui.activity.MovieDetailActivity;
import cz.eman.infinitescroll.ui.adapter.MovieAdapter;
import retrofit.client.Response;

public class MovieInfiniteListFragment extends ListFragment
        implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private int currentPage = 1;
    private int itemsPerPage = 5;
    private int totalPages;

    private RestClient restClient;
    private MovieRestService movieRestService;
    private MovieAdapter adapter;
    private int threshold = 0;
    private View loadProgressView;
    private TextView descriptionView;
    private TextView nomoreDataView;
    private TextView loadButtonView;


    public MovieInfiniteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_movie_infinite_list, container, false);

        descriptionView = (TextView) inflater.inflate(R.layout.view_information, null)
                .findViewById(R.id.header);
        descriptionView.setText(Html.fromHtml(getString(R.string.about_info)));

        loadProgressView = inflater.inflate(R.layout.view_loadingprogress, null)
                .findViewById(R.id.footer);
        loadButtonView = (TextView) inflater.inflate(R.layout.view_loaddata, null)
                .findViewById(R.id.footer);
        loadButtonView.setOnClickListener(onLoadButtonClick);
        nomoreDataView = (TextView) inflater.inflate(R.layout.view_nomoredata, null)
                .findViewById(R.id.footer);

        restClient = new RestClient();
        movieRestService = restClient.getMovieService();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().addHeaderView(descriptionView);
        getListView().addFooterView(loadProgressView);
        showingProgress = true;
        adapter = new MovieAdapter(getActivity().getApplicationContext());
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
        // load next if possible
        if(currentPage < totalPages) {
            currentPage++;
            loadData(currentPage);
        }
    }

    private void loadData(int currentPage) {
        showLoading();

        movieRestService.getMovies(currentPage, itemsPerPage, new RestCallback<API>() {
            @Override
            public void success(API api, Response response) {
                Log.d("APP", "Loaded movies " + api.getMovies());
                Log.d("APP", "Loaded " + api.getMovies().size());

                // update number of total pages
                totalPages = api.getTotal() / itemsPerPage;
                // handle integer rounding proble
                if(api.getTotal() > totalPages*itemsPerPage) {
                    totalPages++;
                }

                for (Movie m : api.getMovies()) {
                    adapter.add(m);
                    m.getPosters().save();
                    Log.d("APP", "poster "+m.getPosters().getThumbnail());
                    for (AbridgedCast c : m.getAbridgedCast()) {
                        c.save();
                    }
                    m.save();
                }
                adapter.notifyDataSetChanged();

                doneLoading();
            }

            @Override
            public void failure(RestError error) {
                Log.d("APP", "Error "+error.getError());
                getListView().removeFooterView(loadProgressView);
                doneLoading();

                // If possible, load data from offline mode
                // but only if we had trouble in connection all the time
                if(adapter.getCount() == 0) {
                    List<Movie> movieList = MovieDbService.getMovies();
                    for (Movie m : movieList) adapter.add(m);

                    Toast.makeText(getActivity().getApplicationContext(),
                            getString(R.string.ERROR_LOAD_DATA_USE_OFFLINE), Toast.LENGTH_LONG)
                            .show();
                    getListView().removeFooterView(loadButtonView);
                    showingLoadButton = false;

                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            getString(R.string.ERROR_LOAD_DATA), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    // for such a simple thing we need locking ... omg
    private volatile boolean showingProgress   = false;
    private volatile boolean showingLoadButton = false;
    private volatile boolean showingNoMoreData = false;
    private synchronized void showLoading() {
        if(showingLoadButton) {
            getListView().removeFooterView(loadButtonView);
            showingLoadButton = false;
        }

        getListView().addFooterView(loadProgressView);
        showingProgress = true;
    }
    private synchronized void doneLoading() {
        if(showingProgress) {
            getListView().removeFooterView(loadProgressView);
            showingProgress = false;
        }

        if(currentPage == totalPages) {
            getListView().addFooterView(nomoreDataView);
            showingNoMoreData = true;
        } else {
            getListView().addFooterView(loadButtonView);
            showingLoadButton = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MovieDetailFragment detailFragment = (MovieDetailFragment) getFragmentManager()
                .findFragmentById(R.id.detailFragment);
        Integer movieId = adapter.getItem(position-1).getSid();

        if (detailFragment == null) {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieId);
            startActivity(intent);
        } else {
            detailFragment.showMovie(movieId);
        }
    }
}
