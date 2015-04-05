package cz.eman.infinitescroll.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.model.entity.AbridgedCast;
import cz.eman.infinitescroll.model.entity.Movie;
import cz.eman.infinitescroll.model.service.MovieDbService;

public class MovieDetailFragment extends Fragment {

    private TextView selectMovie;
    private TextView titleView;
    private TextView yearView;
    private ImageView thumbnailView;
    private TextView synopsisView;
    private TextView castView;
    private TextView castLabel;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        selectMovie = (TextView) rootView.findViewById(R.id.selectMovie);
        titleView = (TextView) rootView.findViewById(R.id.title);
        yearView = (TextView) rootView.findViewById(R.id.year);
        thumbnailView = (ImageView) rootView.findViewById(R.id.thumbnail);
        synopsisView = (TextView) rootView.findViewById(R.id.synopsis);
        castView = (TextView) rootView.findViewById(R.id.cast);
        castLabel = (TextView) rootView.findViewById(R.id.castLabel);

        selectMovie.setVisibility(View.VISIBLE);
        titleView.setVisibility(View.INVISIBLE);
        yearView.setVisibility(View.INVISIBLE);
        thumbnailView.setVisibility(View.INVISIBLE);
        synopsisView.setVisibility(View.INVISIBLE);
        castLabel.setVisibility(View.INVISIBLE);
        castView.setVisibility(View.INVISIBLE);

        return rootView;
    }

    public void showMovie(Integer movieId) {
        Log.d("APP", "show movie id " + movieId);

        selectMovie.setVisibility(View.INVISIBLE);
        titleView.setVisibility(View.VISIBLE);
        yearView.setVisibility(View.VISIBLE);
        thumbnailView.setVisibility(View.VISIBLE);
        synopsisView.setVisibility(View.VISIBLE);
        castLabel.setVisibility(View.VISIBLE);
        castView.setVisibility(View.VISIBLE);

        Movie movie = MovieDbService.getMovieById(movieId);
        titleView.setText(movie.getTitle());
        yearView.setText("Year: "+movie.getYear());
        Picasso.with(getActivity())
                .load(movie.getPosters().getThumbnail())
                .error(R.drawable.ic_list_nopreview)
                .into(thumbnailView);
        synopsisView.setText(movie.getSynopsis());

        String cast = "";
        if(movie.getAbridgedCast().size() > 0) {
            int i = 0;
            for (AbridgedCast c : movie.getAbridgedCast()) {
                cast += " ● " +c.getName() +"\n";
                i++;
                if(i > 6) {
                    cast += "… and many more.";
                    break;
                }
            }
        } else {
            cast += "No cast information available";
        }
        castView.setText(cast);
    }
}