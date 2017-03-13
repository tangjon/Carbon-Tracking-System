package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cmpt276.jade.carbontracker.R;

public class UtilitiesAdapter extends ArrayAdapter{

    public UtilitiesAdapter(Context context, ArrayList<Object> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object o = getItem(position);       // ***

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.utility_list, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);

        tvName.setText(o.toString());       // ***
        tvDate.setText("Dummy date");       // ***

        return convertView;
    }
}
