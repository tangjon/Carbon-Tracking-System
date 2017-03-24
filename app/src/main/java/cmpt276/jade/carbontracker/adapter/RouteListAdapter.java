package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cmpt276.jade.carbontracker.R;

public class RouteListAdapter extends ArrayAdapter<String> {

    private int RouteMode;

    public RouteListAdapter(Context context, String[] item, int mode) {
        super(context, R.layout.item_route, item);
        RouteMode=mode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckys = LayoutInflater.from(getContext());
        View OneBucky = buckys.inflate(R.layout.item_route, parent, false);

        String route=getItem(position);
        TextView tv=(TextView) OneBucky.findViewById(R.id.new_routes_list);

        ImageView image=(ImageView)  OneBucky.findViewById(R.id.imageBus);

        tv.setText(route);

        if(RouteMode==1) {image.setImageResource(R.drawable.car);}
        else if(RouteMode==2) {image.setImageResource(R.drawable.bike);}
        else if(RouteMode==3) {image.setImageResource(R.drawable.bus);}
        else if(RouteMode==4) {image.setImageResource(R.drawable.skytrain);}
        return OneBucky;
    }
}
