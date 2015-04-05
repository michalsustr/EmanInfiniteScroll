package cz.eman.infinitescroll.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import cz.eman.infinitescroll.R;

abstract public class InfiniteListFragment extends ListFragment
        implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private int currentPage = 1;
    private int itemsPerPage;
    private Integer totalPages;
    private Integer scrollToIndex;

    private int loadThreshold = 0;
    private View loadProgressView;
    private View nomoreDataView;
    private View loadButtonView;


    public InfiniteListFragment(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    /**
     * Load data according to current page
     *
     * This method must explicitly call showLoading and doneLoading
     * to indicate progress of the loading.
     *
     * Somewhere the number of totalPages must be set (either in constructor
     * or here).
     *
     * @param currentPage int >= 1
     */
    abstract void loadData(int currentPage);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_infinite_list, container, false);

        // Initialize all footer views
        loadProgressView = inflater.inflate(R.layout.view_loadingprogress, null)
                .findViewById(R.id.footer);
        loadButtonView = (TextView) inflater.inflate(R.layout.view_loaddata, null)
                .findViewById(R.id.footer);
        loadButtonView.setOnClickListener(onLoadButtonClick);
        nomoreDataView = (TextView) inflater.inflate(R.layout.view_nomoredata, null)
                .findViewById(R.id.footer);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnScrollListener(this);
        getListView().setOnItemClickListener(this);

        // Restoring from saved state
        if(scrollToIndex != null) {
            getListView().setSelectionFromTop(scrollToIndex, 0);
            scrollToIndex = null;

            // Check for reaching end of records
            if(totalPages != null && currentPage == totalPages) {
                getListView().addFooterView(nomoreDataView);
                showingNoMoreData = true;
            }
        } else {

            // Not restoring, start loading data from server
            getListView().addFooterView(loadProgressView, null, false);
            showingProgress = true;
            loadData(currentPage);
        }
    }

    protected View.OnClickListener onLoadButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Do not load if accessing from database
            // (that should be the only reason for totalPages to be null)
            if(totalPages == null) {
                return;
            }

            if(currentPage < totalPages) {
                loadNextPage();
            } else {
                if(!showingNoMoreData) {
                    Toast.makeText(getActivity(), "No more available items", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            if (getListView().getLastVisiblePosition() >= getListView().getCount() - 1 - loadThreshold) {
                loadNextPage();
            }
        }
    }

    protected void loadNextPage() {
        // Do not load if accessing from database
        // (that should be the only reason for totalPages to be null)
        if(totalPages == null) {
            return;
        }

        // load next if possible
        if(currentPage < totalPages) {
            currentPage++;
            loadData(currentPage);
        }
    }

    // for such a simple thing we need locking ... omg
    private boolean showingProgress   = false;
    private boolean showingLoadButton = false;
    private boolean showingNoMoreData = false;
    protected synchronized void showLoading() {
        if(showingLoadButton) {
            getListView().removeFooterView(loadButtonView);
            showingLoadButton = false;
        }

        getListView().addFooterView(loadProgressView, null, false);
        showingProgress = true;
    }
    protected synchronized void doneLoading() {
        if(showingProgress) {
            getListView().removeFooterView(loadProgressView);
            showingProgress = false;
        }

        if(totalPages == null || (totalPages != null && currentPage == totalPages)) {
            getListView().addFooterView(nomoreDataView, null, false);
            showingNoMoreData = true;
        } else {
            getListView().addFooterView(loadButtonView, null, false);
            showingLoadButton = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

    // Getters & setters ...
    public int getCurrentPage() {
        return currentPage;
    }
    public int getItemsPerPage() {
        return itemsPerPage;
    }
    public Integer getTotalPages() {
        return totalPages;
    }
    public void setScrollToIndex(Integer scrollToIndex) {
        this.scrollToIndex = scrollToIndex;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
    public void setLoadThreshold(int loadThreshold) {
        this.loadThreshold = loadThreshold;
    }
}
