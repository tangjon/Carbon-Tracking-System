package cmpt276.jade.carbontracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.model.Skytrain;

/**
 * Created by Sean on 21/03/2017.
 * <p>
 * Displays the skytrain list
 */

public class SkytrainListAdaptor extends ArrayAdapter<Skytrain> {

    Context mContext;

    public SkytrainListAdaptor(Context context, List<Skytrain> item) {
        super(context, R.layout.item_skytrain, item);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(getContext());
        View v = inf.inflate(R.layout.item_skytrain, parent, false);

        Skytrain train = getItem(position);
        ImageView image = (ImageView) v.findViewById(R.id.skytrainImage);
        image.setImageResource(train.getImageId());
        TextView tv = (TextView) v.findViewById(R.id.skytrainListItem);
        tv.setText(train.getDetails());


        return v;
    }

}
