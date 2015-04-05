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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.model.entity.AbridgedCast;
import cz.eman.infinitescroll.model.entity.Movie;
import cz.eman.infinitescroll.model.service.MovieDbService;

public class MovieDetailFragment extends Fragment {

    private TextView titleView;
    private TextView yearView;
    private ImageView thumbnailView;
    private TextView synopsisView;
    private TextView castView;

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        titleView = (TextView) rootView.findViewById(R.id.title);
        yearView = (TextView) rootView.findViewById(R.id.year);
        thumbnailView = (ImageView) rootView.findViewById(R.id.thumbnail);
        synopsisView = (TextView) rootView.findViewById(R.id.synopsis);
        castView = (TextView) rootView.findViewById(R.id.cast);

        return rootView;
    }

    public void showMovie(Integer movieId) {
        Log.d("APP", "show movie id " + movieId);

        Movie movie = MovieDbService.getMovieById(movieId);
        new DownloadImage().execute(movie.getPosters().getThumbnail());
        titleView.setText(movie.getTitle());
        yearView.setText("Year: "+movie.getYear());
        synopsisView.setText(movie.getSynopsis());

        String cast = "";
        if(movie.getAbridgedCast().size() > 0) {
            Log.d("APP", movie.getAbridgedCast().toString());
            for (AbridgedCast c : movie.getAbridgedCast()) {
                cast += c.getName() +"\n";
                Log.d("APP", "name "+c.getName());
            }
        } else {
            cast += "No cast information available";
        }
        castView.setTag(cast);
    }

    private void setThumbnail(Drawable drawable)
    {
        thumbnailView.setImageDrawable(drawable);
    }

    public class DownloadImage extends AsyncTask<String, Integer, Drawable> {
        @Override
        protected Drawable doInBackground(String... arg0) {
            return downloadImage(arg0[0]);
        }

        protected void onPostExecute(Drawable image) {
            setThumbnail(image);
        }

        private Drawable downloadImage(String _url)
        {
            Log.d("APP", "downloading "+_url);
            //Prepare to download image
            URL url;
            BufferedOutputStream out;
            InputStream in;
            BufferedInputStream buf;

            //BufferedInputStream buf;
            try {
                url = new URL(_url);
                in = url.openStream();

                // Read the inputstream
                buf = new BufferedInputStream(in);

                // Convert the BufferedInputStream to a Bitmap
                Bitmap bMap = BitmapFactory.decodeStream(buf);
                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                return new BitmapDrawable(getActivity().getResources(), bMap);

            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }

            return null;
        }

    }
}