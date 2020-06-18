package com.shafiq.asifa.taleemforalll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asifa on 6/26/2019.
 */

public class UniAdapter  extends ArrayAdapter<University> {


    public UniAdapter(Context context, int resource, List<University> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_view, parent, false);
        }
        University current = getItem(position);

        TextView textView = (TextView)listItemView.findViewById(R.id.uniTitle);
        ImageView imageView = (ImageView)listItemView.findViewById(R.id.uniLogo);

        textView.setText(current.getUniName());
        imageView.setImageResource(current.getUniLogo());

        return  listItemView;
    }
}
