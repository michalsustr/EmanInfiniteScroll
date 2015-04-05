package cz.eman.infinitescroll.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cz.eman.infinitescroll.R;
import cz.eman.infinitescroll.model.entity.Movie;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private LayoutInflater mInflater;
    protected static int mRowResource = R.layout.row_movie;

    public MovieAdapter(Context context) {
        super(context, mRowResource, new ArrayList<Movie>());
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolderItem {
        TextView titleTextView;
        TextView yearTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if(convertView==null){
            // inflate the layout
            convertView = mInflater.inflate(mRowResource, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.titleTextView =
                    (TextView) convertView.findViewById(R.id.titleTextView);
            viewHolder.yearTextView =
                    (TextView) convertView.findViewById(R.id.yearTextView);

            // store the holder with the view.
            convertView.setTag(viewHolder);
        }else{
            // we've just avoided calling findViewById() on resource every time
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        // object item based on the position
        Movie item= getItem(position);

        // assign values if the object is not null
        if(item != null) {
            viewHolder.titleTextView.setText(item.getTitle());
            viewHolder.yearTextView.setText(""+item.getYear());
        }

        return convertView;
    }

    /**
     * Helper saving state
     * @return
     */
    public int[] getIds() {
        int[] ids = new int[getCount()];
        for (int i = 0; i < getCount(); i++) {
            ids[i] = getItem(i).getSid();
        }
        return ids;
    }

}
