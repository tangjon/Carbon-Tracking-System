package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cmpt276.jade.carbontracker.R;

public class JourneyListAdapter extends ArrayAdapter<String> {

    public JourneyListAdapter(Context context, String[] item) {
        super(context, R.layout.journey_list, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater buckys = LayoutInflater.from(getContext());
        View OneBucky = buckys.inflate(R.layout.journey_list, parent, false);

        String route=getItem(position);
        TextView tv=(TextView) OneBucky.findViewById(R.id.new_journey_list);

        tv.setText(route);
        return OneBucky;
    }
}
