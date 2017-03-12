package cmpt276.jade.carbontracker.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import cmpt276.jade.carbontracker.CarbonFootprintActivity;
import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarbonFootPrintFragment extends Fragment {

    private JourneyCollection journeyCollection = Emission.getInstance().getJourneyCollection();
    private PieChart pieChart;
    private TableLayout table;
    private Boolean pieShown = true;
    private final int NUM_ENTRIES = journeyCollection.countJourneys();

    private String emissionDate[] = new String[NUM_ENTRIES];
    private String emissionRouteNames[] = new String[NUM_ENTRIES];
    private float emissionValues[] = new float[NUM_ENTRIES];
    private double emissionDistance[] = new double[NUM_ENTRIES];
    private String emissionVehicleNames[] = new String[NUM_ENTRIES];

    // View of the Fragment
    private View v;

    public CarbonFootPrintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set ToolBar Name
        getActivity().setTitle(R.string.nav_overview);

        // Create a View
        v = inflater.inflate(R.layout.fragment_carbon_foot_print, container, false);
        loadData();

        setupTable();
        setupPieChart();
        setupButton();

        return v;
    }

    private void setupTable() {
        table = (TableLayout) v.findViewById(R.id.tableFootprint);
        table.setShrinkAllColumns(true);

        TableRow labelRow = new TableRow(getContext());
        String[] labels = getResources().getStringArray(R.array.label_table);
        for (int i = 0; i < labels.length; ++i) {
            TextView tv = new TextView(getContext());
            tv.setText(labels[i]);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTypeface(null, Typeface.BOLD);
            labelRow.addView(tv);
        }
        table.addView(labelRow);

        for (int row = 0; row < NUM_ENTRIES; ++row) {
            TableRow tableRow = new TableRow(getContext());
            float f;
            table.addView(tableRow);

            for (int col = 0; col < 5; ++col) {
                TextView tv = new TextView(getContext());
                switch (col) {
                    case 0:
                        tv.setText(emissionDate[row]);
                        break;
                    case 1:
                        tv.setText(emissionRouteNames[row]);
                        break;
                    case 2:
                        f = (float) emissionDistance[row];
                        f = Math.round(f);
                        tv.setText(String.valueOf(f));
                        break;
                    case 3:
                        tv.setText(emissionVehicleNames[row]);
                        break;
                    case 4:
                        f = emissionValues[row];
                        f = Math.round(f);
                        tv.setText(String.valueOf(f));
                        break;
                }
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tableRow.addView(tv);
            }
        }

        table.setVisibility(View.INVISIBLE); // starts invisible
    }

    private void loadData() {
        emissionRouteNames = journeyCollection.getJourneyDetails();
        Journey j;

        for (int i = 0; i < NUM_ENTRIES; ++i) {
            j = journeyCollection.getJourney(i);
            emissionDate[i] = j.getDate();
            emissionRouteNames[i] = j.getName();
            emissionDistance[i] = j.getRoute().getCityDistance() + j.getRoute().getCityDistance();
            emissionVehicleNames[i] = j.getCar().getNickname();
            emissionValues[i] = (float) j.getTotalTravelled();
        }
    }

    private void setupButton() {
        Button btn = (Button) v.findViewById(R.id.btn_graph_toggle);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pieShown) {
                    pieChart.setVisibility(View.INVISIBLE);
                    table.setVisibility(View.VISIBLE);
                    pieShown = false;
                } else {
                    pieChart.setVisibility(View.VISIBLE);
                    table.setVisibility(View.INVISIBLE);
                    pieChart.invalidate();
                    pieShown = true;
                }
            }
        });
    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < NUM_ENTRIES; ++i)
            pieEntries.add(new PieEntry(emissionValues[i], emissionRouteNames[i]));

        PieDataSet dataSet = new PieDataSet(pieEntries, getResources().getString(R.string.label_graph_title));
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(12f);

        pieChart = (PieChart) v.findViewById(R.id.pie_graph);
        pieChart.setData(data);
        /*Legend l = pieChart.getLegend();
        l.setWordWrapEnabled(true);*/
        pieChart.getLegend().setEnabled(false);

        Description desc = new Description();
        desc.setEnabled(false);
        pieChart.setDescription(desc);
        pieChart.animateY(600);
        pieChart.invalidate();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, CarbonFootprintActivity.class);
    }

}
