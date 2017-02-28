package cmpt276.jade.carbontracker.adapter;

import android.widget.ArrayAdapter;

/**
 * Created by tangj on 2/28/2017.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.model.Car;

public class CustomSpinner extends ArrayAdapter<Car>{

    private List<Car> mItems;
    private Context mContext;

    public CustomSpinner(Context context, List<Car> items) {
        super(context, R.layout.custom_spinner, items);
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = convertView;
        if( itemView == null){
            itemView = inflater.inflate(R.layout.custom_spinner,parent,false);
        }
        // Get Car
        Car currentCar = mItems.get(position);

        TextView tv_year = (TextView) itemView.findViewById(R.id.tv_year_display);
        tv_year.setText(Integer.toString(currentCar.getYear()));
        TextView tv_UCity = (TextView) itemView.findViewById(R.id.tv_ucity);
        tv_UCity.setText(Double.toString(currentCar.getuCity()));
        TextView tv_UHighway = (TextView) itemView.findViewById(R.id.tv_uhighway);
        tv_UCity.setText(Double.toString(currentCar.getuHighway()));
        TextView tv_carbon_tail_pipe = (TextView) itemView.findViewById(R.id.tv_carbon_tail_pipe);
        tv_UCity.setText(Double.toString(currentCar.getCarbonTailPipe()));



        return itemView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Handle Null views
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = convertView;
        if( itemView == null){
            itemView = inflater.inflate(R.layout.custom_spinner,parent,false);
        }
        Car currentCar = mItems.get(position);

        TextView tv_year = (TextView) itemView.findViewById(R.id.tv_year_display);
        tv_year.setText(Integer.toString(currentCar.getYear()));




        return itemView;
    }

}
