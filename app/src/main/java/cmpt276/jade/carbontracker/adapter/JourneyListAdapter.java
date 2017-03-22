package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.JourneyCollection;



public class JourneyListAdapter extends ArrayAdapter<String> {
    JourneyCollection list;
    public JourneyListAdapter(Context context, String[] item, JourneyCollection list) {
        super(context, R.layout.journey_list, item);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckys = LayoutInflater.from(getContext());
        View OneBucky = buckys.inflate(R.layout.journey_list, parent, false);

        String route=getItem(position);
        TextView tv=(TextView) OneBucky.findViewById(R.id.new_journey_list);

        ImageView image=(ImageView)  OneBucky.findViewById(R.id.journeyListImage);

        tv.setText(route);

        if(list.countJourneys() != 0) {
            if (list.getJourney(position).getTransType().getTransMode().equals(Transport.CAR)) {
                image.setImageResource(R.drawable.car);
            } else if (list.getJourney(position).getTransType().getTransMode().equals(Transport.BIKE)) {
                image.setImageResource(R.drawable.bike);
            } else if (list.getJourney(position).getTransType().getTransMode().equals(Transport.BUS)) {
                image.setImageResource(R.drawable.bus);
            } else if (list.getJourney(position).getTransType().getTransMode().equals(Transport.SKYTRAIN)) {
                image.setImageResource(R.drawable.skytrain);
            }
        }

        return OneBucky;
    }
}
