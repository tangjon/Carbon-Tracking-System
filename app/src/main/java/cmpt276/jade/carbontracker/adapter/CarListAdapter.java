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
 * Created by tangj on 3/2/2017.
 */

public class CarListAdapter extends ArrayAdapter<Car> {
    private List<Car> mItems;
    private Context mContext;

    public CarListAdapter(Context context, List<Car> items) {
        super(context, R.layout.custom_spinner_dropdown, items);
        this.mContext = context;
        this.mItems = items;
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
            itemView = inflater.inflate(R.layout.car_item_list, parent, false);
        }
        Car currentCar = mItems.get(position);

        String string = currentCar.getMake() + " : " + currentCar.getModel() + " : " + currentCar.getYear();
        setUpTextView(itemView, R.id.tv_car_item, string);

        return itemView;
    }
}
