package cmpt276.jade.carbontracker.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cmpt276.jade.carbontracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarbonFootPrintFragment extends Fragment {


    public CarbonFootPrintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carbon_foot_print, container, false);
    }

}
