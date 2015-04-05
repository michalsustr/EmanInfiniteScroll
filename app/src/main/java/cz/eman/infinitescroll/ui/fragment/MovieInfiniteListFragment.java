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
import android.widget.RelativeLayout;
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

public class MovieInfiniteListFragment extends InfiniteListFragment {
    private static final String SAVED_ADAPTER_MOVIE_IDS = "movieIds";
    private static final String SAVED_MOVIE_INDEX = "movieIdx";
    private static final String SAVED_CURRENT_PAGE = "currentPage";
    private static final String SAVED_TOTAL_PAGES = "totalPages";

    private static final Integer ITEMS_PER_PAGE = 5;

    private RestClient restClient;
    private MovieRestService movieRestService;
    private MovieAdapter adapter;
    private RelativeLayout descriptionView;


    public MovieInfiniteListFragment() {
        super(ITEMS_PER_PAGE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore movie list
            int[] ids = savedInstanceState.getIntArray(SAVED_ADAPTER_MOVIE_IDS);
            if (ids != null) {
                adapter = new MovieAdapter(getActivity().getApplicationContext());
                for (int i = 0; i < ids.length; i++) {
                    adapter.add(MovieDbService.getMovieById(ids[i]));
                }
            }
            // Restore position later, when getListView() is available
            setScrollToIndex(savedInstanceState.getInt(SAVED_MOVIE_INDEX));
            setCurrentPage(savedInstanceState.getInt(SAVED_CURRENT_PAGE));
            setTotalPages(savedInstanceState.getInt(SAVED_TOTAL_PAGES));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        descriptionView = (RelativeLayout) inflater.inflate(R.layout.view_information, null)
                .findViewById(R.id.header);
        ((TextView) descriptionView.findViewById(R.id.headerInfo))
                .setText(Html.fromHtml(getString(R.string.about_info)));

        restClient = new RestClient();
        movieRestService = restClient.getMovieService();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().addHeaderView(descriptionView, null, false);

        if(adapter == null) {
            adapter = new MovieAdapter(getActivity().getApplicationContext());
        }
        setListAdapter(adapter);
    }

    @Override
    protected void loadData(int currentPage) {
        showLoading();

        movieRestService.getMovies(currentPage, ITEMS_PER_PAGE, new RestCallback<API>() {
            @Override
            public void success(API api, Response response) {
                Log.d("APP", "Loaded movies " + api.getMovies());
                Log.d("APP", "Loaded " + api.getMovies().size());

                // update number of total pages
                int totalPages = api.getTotal() / ITEMS_PER_PAGE;
                // handle integer rounding proble
                if(api.getTotal() > totalPages*ITEMS_PER_PAGE) {
                    totalPages++;
                }
                setTotalPages(totalPages);

                for (Movie m : api.getMovies()) {
                    adapter.add(m);
                    m.getPosters().save();
                    for (AbridgedCast c : m.getAbridgedCast()) {
                        c.setMovieId(m.getSid());
                        c.save();
                    }
                    // This can throw exception because of movie's unique id, but that's ok.
                    m.save();
                }
                adapter.notifyDataSetChanged();

                doneLoading();
            }

            @Override
            public void failure(RestError error) {
                Log.d("APP", "Error "+error.getError());
                doneLoading();

                // If possible, load data from offline mode
                // but only if we had trouble in connection all the time
                if(adapter.getCount() == 0) {
                    List<Movie> movieList = MovieDbService.getMovies();
                    for (Movie m : movieList) adapter.add(m);
                    setTotalPages(movieList.size()/ITEMS_PER_PAGE);

                    Toast.makeText(getActivity().getApplicationContext(),
                            getString(R.string.ERROR_LOAD_DATA_USE_OFFLINE), Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            getString(R.string.ERROR_LOAD_DATA), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Prevent clicking of header / footer
        if(position == 0 || position == adapter.getCount()+1) {
            return;
        }

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

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);

        // Note: getValues() is a method in your ArrayAdaptor subclass
        int[] ids = adapter.getIds();
        savedState.putIntArray(SAVED_ADAPTER_MOVIE_IDS, ids);
        savedState.putInt(SAVED_MOVIE_INDEX, getListView().getFirstVisiblePosition());
        savedState.putInt(SAVED_CURRENT_PAGE, getCurrentPage());
        savedState.putInt(SAVED_TOTAL_PAGES, getTotalPages());

    }
}
