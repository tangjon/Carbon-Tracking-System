package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cmpt276.jade.carbontracker.R;

/**
 * Created by Sean on 21/03/2017.
 * Class:
 * Description:
 *Creates the bus list
 * Bugs:
 */

public class BusListAdapter extends ArrayAdapter<String> {

    public BusListAdapter(Context context, String[] item) {
        super(context, R.layout.bus_list, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(getContext());
        View v = inf.inflate(R.layout.bus_list, parent, false);

        String route=getItem(position);
        TextView tv=(TextView) v.findViewById(R.id.busListItem);


        ImageView image=(ImageView)  v.findViewById(R.id.busImage);
        image.setImageResource(R.drawable.bus);
        tv.setText(route);


        return v;
    }

}
