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

public class CustomSpinner extends ArrayAdapter<Car> {

    private List<Car> mItems;
    private Context mContext;

    public CustomSpinner(Context context, List<Car> items) {
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

        TextView tv_year = (TextView) itemView.findViewById(R.id.tv_year_display);
        tv_year.setText(Integer.toString(currentCar.getYear()));
        TextView tv_UCity = (TextView) itemView.findViewById(R.id.tv_ucity);
        tv_UCity.setText(Double.toString(currentCar.getuCity()));
        TextView tv_UHighway = (TextView) itemView.findViewById(R.id.tv_uhighway);
        tv_UHighway.setText(Double.toString(currentCar.getuHighway()));
        TextView tv_carbon_tail_pipe = (TextView) itemView.findViewById(R.id.tv_carbon_tail_pipe);
        tv_carbon_tail_pipe.setText(Double.toString(currentCar.getCarbonTailPipe()));


        return itemView;
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

        TextView tv_year = (TextView) itemView.findViewById(R.id.tv_item);
        tv_year.setText(Integer.toString(currentCar.getYear()));


        return itemView;
    }

}
