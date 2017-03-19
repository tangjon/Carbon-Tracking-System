package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Graph;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Utilities;

/*
 *  Displays user's carbon footprint in graphs or table
 */
public class CarbonFootprintActivity extends AppCompatActivity {

    private JourneyCollection journeyCollection = Emission.getInstance().getJourneyCollection();
    private Utilities utilities = Emission.getInstance().getUtilities();
    private PieChart pieChart;
    private BarChart barChart;
    private TableLayout table;
    //private Boolean pieShown = true;
    private List<Bill> billsElec;
    private List<Bill> billsGas;
    private final int NUM_ENTRIES = journeyCollection.countJourneys();

    private int mode = 0;

    private String emissionDate[] = new String[NUM_ENTRIES];
    private String emissionRouteNames[] = new String[NUM_ENTRIES];
    private float emissionValues[] = new float[NUM_ENTRIES];
    private double emissionDistance[] = new double[NUM_ENTRIES];
    private String emissionVehicleNames[] = new String[NUM_ENTRIES];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint);

        loadData();

        setupTable();
        setupPieChart();
        setupBarChart();
        setupButton();
    }

    private void setupTable() {
        table = (TableLayout) findViewById(R.id.tableFootprint);
        table.setShrinkAllColumns(true);

        // Journeys
        TableRow labelRow = new TableRow(this);
        String[] labels = getResources().getStringArray(R.array.label_table);
        for (int i = 0; i < labels.length; ++i) {
            TextView tv = new TextView(this);
            tv.setText(labels[i]);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTypeface(null, Typeface.BOLD);
            labelRow.addView(tv);
        }
        table.addView(labelRow);

        for (int row = 0; row < NUM_ENTRIES; ++row) {
            TableRow tableRow = new TableRow(this);
            float f;
            table.addView(tableRow);

            for (int col = 0; col < 5; ++col) {
                TextView tv = new TextView(this);
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

        // Electricity bills
        TableRow labelRowElec = new TableRow(this);
        String[] labelsElec = getResources().getStringArray(R.array.label_table_elec);
        for (int i = 0; i < labelsElec.length; ++i) {
            TextView tv = new TextView(this);
            tv.setText(labelsElec[i]);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTypeface(null, Typeface.BOLD);
            labelRowElec.addView(tv);
        }
        table.addView(labelRowElec);

        for (Bill b : billsElec) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);

            for (int col = 0; col < labelsElec.length; ++col) {
                TextView tv = new TextView(this);
                switch (col) {
                    case 0:
                        tv.setText(Emission.DATE_FORMAT.format(b.getStartDate()));
                        break;
                    case 1:
                        tv.setText(Emission.DATE_FORMAT.format(b.getEndDate()));
                        break;
                    case 2:
                        tv.setText(String.valueOf(Emission.round(b.getInput())));
                        break;
                    case 3:
                        tv.setText(String.valueOf(Emission.round(b.getEmissionTotal())));
                        break;
                    case 4:
                        tv.setText(String.valueOf(Emission.round(b.getEmissionAvg())));
                        break;
                }
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tableRow.addView(tv);
            }
        }

        // Gas bills
        TableRow labelRowGas = new TableRow(this);
        String[] labelsGas = getResources().getStringArray(R.array.label_table_gas);
        for (int i = 0; i < labelsGas.length; ++i) {
            TextView tv = new TextView(this);
            tv.setText(labelsGas[i]);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTypeface(null, Typeface.BOLD);
            labelRowGas.addView(tv);
        }
        table.addView(labelRowGas);

        for (Bill b : billsGas) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);

            for (int col = 0; col < labelsGas.length; ++col) {
                TextView tv = new TextView(this);
                switch (col) {
                    case 0:
                        tv.setText(Emission.DATE_FORMAT.format(b.getStartDate()));
                        //tv.setTextSize(10f);
                        break;
                    case 1:
                        tv.setText(Emission.DATE_FORMAT.format(b.getEndDate()));
                        //tv.setTextSize(10f);
                        break;
                    case 2:
                        tv.setText(String.valueOf(Emission.round(b.getInput())));
                        break;
                    case 3:
                        tv.setText(String.valueOf(Emission.round(b.getEmissionTotal())));
                        break;
                    case 4:
                        tv.setText(String.valueOf(Emission.round(b.getEmissionAvg())));
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
            emissionVehicleNames[i] = j.getTransType().getCar().getNickname();
            emissionValues[i] = (float) Math.round(j.getTotalTravelled());
        }

        billsElec = utilities.getListBillElec();
        billsGas = utilities.getListBillGas();
    }

    private void setupButton() {
        Button btn = (Button) findViewById(R.id.btn_graph_toggle);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mode % 3){
                    case 0:
                        pieChart.setVisibility(View.INVISIBLE);
                        table.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.VISIBLE);
                        barChart.invalidate();
                        break;
                    case 1:
                        pieChart.setVisibility(View.INVISIBLE);
                        table.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        pieChart.setVisibility(View.VISIBLE);
                        table.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        pieChart.invalidate();
                        break;
                }
                ++mode;
            }
        });
    }

    private void setupPieChart() {
        PieData data = Graph.getPieData(getString(R.string.label_graph_title), 0, null, null, null);
        data.setValueTextSize(12f);

        pieChart = (PieChart) findViewById(R.id.pie_graph);
        pieChart.setData(data);
        pieChart.getLegend().setEnabled(false);

        Description desc = new Description();
        desc.setEnabled(false);
        pieChart.setDescription(desc);
        pieChart.animateY(600);
        pieChart.invalidate();
    }

    private void setupBarChart() {
        BarData data = Graph.getBarData(getString(R.string.label_graph_title), 0, null, null, null);
        data.setValueTextSize(12f);

        barChart = (BarChart) findViewById(R.id.bar_graph);
        barChart.setData(data);
        barChart.getLegend().setEnabled(false);

        String[] labels = getResources().getStringArray(R.array.label_bar_graph);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new XAxisValueFormatter(labels));
        xAxis.setGranularity(1f);

        Description desc = new Description();
        desc.setEnabled(false);

        barChart.setDescription(desc);
        barChart.animateY(600);
        barChart.setFitBars(true);
        barChart.invalidate();
        barChart.setVisibility(View.INVISIBLE);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, CarbonFootprintActivity.class);
    }

    private class XAxisValueFormatter implements IAxisValueFormatter {
        private String[] labels;

        public XAxisValueFormatter(String[] labels) {
            this.labels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return labels[(int) value];
        }
    }
}
