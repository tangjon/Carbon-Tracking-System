package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Emission;

public class UtilitiesAdapter extends ArrayAdapter{

    public UtilitiesAdapter(Context context, List list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bill bill = (Bill) getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.utility_list, parent, false);

        String date = Emission.DATE_FORMAT.format(bill.getStartDate())
                + " - " + Emission.DATE_FORMAT.format(bill.getEndDate());

        TextView tvEmission = (TextView) convertView.findViewById(R.id.tvEmission);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);

        tvEmission.setText(String.valueOf(Math.round(bill.getEmissionTotal())) + "Kg CO2");
        tvDate.setText(date);

        return convertView;
    }
}
