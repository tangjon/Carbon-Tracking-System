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
 *
 * Displays the skytrain list
 */

public class SkytrainListAdaptor extends ArrayAdapter<String> {

    public SkytrainListAdaptor(Context context, String[] item) {
        super(context, R.layout.item_skytrain, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(getContext());
        View v = inf.inflate(R.layout.item_skytrain, parent, false);

        String route=getItem(position);
        TextView tv=(TextView) v.findViewById(R.id.skytrainListItem);


        ImageView image=(ImageView)  v.findViewById(R.id.skytrainImage);
        image.setImageResource(R.drawable.skytrain);
        tv.setText(route);


        return v;
    }

}
