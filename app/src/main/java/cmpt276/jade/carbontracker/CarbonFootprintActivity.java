package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.JourneyCollection;

public class CarbonFootprintActivity extends AppCompatActivity {

    private JourneyCollection journeyCollection = Emission.getInstance().getJourneyCollection();
    private PieChart pieChart;
    private TableLayout table;
    private Boolean pieShown = true;
    private final int NUM_ENTRIES = journeyCollection.countJourneys();

    private String emissionDate[] = new String[NUM_ENTRIES];
    private String emissionNames[] = new String[NUM_ENTRIES];
    private float emissionValues[] = new float[NUM_ENTRIES];
    private double emissionDistance[] = new double[NUM_ENTRIES];
    private String emissionVehicleName[] = new String[NUM_ENTRIES];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint);

        loadData();

        setupTable();
        setupPieChart();
        setupButton();
    }

    private void setupTable() {
        table = (TableLayout) findViewById(R.id.tableFootprint);

        TableRow labelRow = new TableRow(this);
        String[] labels = getResources().getStringArray(R.array.label_table);
        for (int i = 0; i < labels.length; ++i) {
            TextView tv = new TextView(this);
            tv.setText(labels[i]);
            labelRow.addView(tv);
        }
        table.addView(labelRow);

        for (int row = 0; row < NUM_ENTRIES; ++row) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);

            for (int col = 0; col < 5; ++col) {
                TextView tv = new TextView(this);
                switch (col) {
                    case 0: tv.setText("date goes here");   // date not implemented
                    case 1: tv.setText(emissionNames[row]);
                    case 2: tv.setText(String.valueOf(emissionDistance[row]));
                    case 3: tv.setText(emissionVehicleName[row]);
                    case 4: tv.setText("carbon goes here"); // carbon not implemented
                }
                tableRow.addView(tv);
            }
        }

        table.setVisibility(View.INVISIBLE); // starts invisible
    }

    private void loadData() {
        emissionNames = journeyCollection.getJourneyDetails();

        for (int i = 0; i < NUM_ENTRIES; ++i) {
            emissionDate[i] = "dummy date";
            emissionNames[i] = journeyCollection.getJourney(i).getRoute().getName();
            emissionDistance[i] = journeyCollection.getJourney(i).getTotalTravelled();
            emissionVehicleName[i] = journeyCollection.getJourney(i).getName();
            emissionValues[i] = 12f;    // dummy data
        }
    }

    private void setupButton() {
        Button btn = (Button) findViewById(R.id.btn_graph_toggle);
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
            pieEntries.add(new PieEntry(emissionValues[i], emissionNames[i]));

        PieDataSet dataSet = new PieDataSet(pieEntries, getResources().getString(R.string.label_graph_title));
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(12f);

        pieChart = (PieChart) findViewById(R.id.pie_graph);
        pieChart.setData(data);

        Description desc = new Description();
        desc.setEnabled(false);
        pieChart.setDescription(desc);
        pieChart.animateY(600);
        pieChart.setVisibility(View.INVISIBLE);
        pieChart.invalidate();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, CarbonFootprintActivity.class);
    }
}
