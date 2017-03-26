package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.model.Bus;

/**
 * Created by Sean on 21/03/2017.
 * Class:
 * Description:
 * Creates the bus list
 * Bugs:
 */

public class BusListAdapter extends ArrayAdapter<Bus> {

    private Context mContext;

    public BusListAdapter(Context context, List<Bus> bus) {
        super(context, R.layout.item_bus, bus);
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(getContext());
        View v = inf.inflate(R.layout.item_bus, parent, false);

        Bus bus = getItem(position);
        TextView tv = (TextView) v.findViewById(R.id.busListItem);
        ImageView image = (ImageView) v.findViewById(R.id.busImage);
        image.setImageResource(R.drawable.bus);
        tv.setText(bus.getDetail());


        return v;
    }

}
