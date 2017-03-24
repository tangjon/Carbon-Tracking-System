package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cmpt276.jade.carbontracker.R;

import static android.content.ContentValues.TAG;

public class RouteListAdapter extends ArrayAdapter<String> {

    private int RouteMode;

    public RouteListAdapter(Context context, String[] item, int mode) {
        super(context, R.layout.item_route, item);
        RouteMode = mode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckys = LayoutInflater.from(getContext());
        View OneBucky = buckys.inflate(R.layout.item_route, parent, false);

        String route = getItem(position);
        TextView tv = (TextView) OneBucky.findViewById(R.id.new_routes_list);

        ImageView image = (ImageView) OneBucky.findViewById(R.id.imageBus);

        tv.setText(route);

        switch (RouteMode) {
            case 5:
                image.setImageResource(R.drawable.walksymbol);
                break;
            case 1:
                image.setImageResource(R.drawable.car);
                break;
            case 2:
                // Temp fix for walks and bikes
                String[] buff = route.split(" ");
                boolean bool = buff[0].contains("Walk");
                if(bool){
                    image.setImageResource(R.drawable.walksymbol);
                } else {
                    image.setImageResource(R.drawable.bike);
                }
                break;
            case 3:
                image.setImageResource(R.drawable.bus);
                break;
            case 4:
                image.setImageResource(R.drawable.skytrain);
                break;
        }
        return OneBucky;
    }
}
