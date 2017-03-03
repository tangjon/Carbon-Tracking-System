package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.model.Car;

/**
 * Created by tangj on 2/28/2017.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<Car> {

    private List<Car> mItems;
    private Context mContext;

    public CustomSpinnerAdapter(Context context, List<Car> items) {
        super(context, R.layout.custom_spinner_dropdown, items);
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.custom_spinner_dropdown, parent, false);
        }
        // Get Car
        Car currentCar = mItems.get(position);

        setUpTextView(itemView, R.id.tv_year_display, Integer.toString(currentCar.getYear()));
        setUpTextView(itemView, R.id.tv_cityMPG, Double.toString(currentCar.getCityMPG()));
        setUpTextView(itemView, R.id.tv_highwayMPG, Double.toString(currentCar.getHighwayMPG()));
        setUpTextView(itemView, R.id.tv_carbon_tail_pipe, Double.toString(currentCar.getCarbonTailPipe()));
        setUpTextView(itemView, R.id.tv_displacement, Double.toString(currentCar.getEngineDispLitres()));
        setUpTextView(itemView, R.id.tv_engine, currentCar.getEngineDescription());
        setUpTextView(itemView, R.id.tv_fuel_cost, Integer.toString(currentCar.getFuelAnnualCost()));
        setUpTextView(itemView, R.id.tv_transmission, currentCar.getTransDescription());




        return itemView;
    }

    private void setUpTextView(View itemView, int tvID, String text) {
        TextView tv_carbon_tail_pipe = (TextView) itemView.findViewById(tvID);
        tv_carbon_tail_pipe.setText(text);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Handle Null views
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.custom_spinner_list, parent, false);
        }
        Car currentCar = mItems.get(position);

        setUpTextView(itemView, R.id.tv_item, Integer.toString(currentCar.getYear()));

        return itemView;
    }

}
