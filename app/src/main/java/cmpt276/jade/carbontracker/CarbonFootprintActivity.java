package cmpt276.jade.carbontracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Graph;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Tip;
import cmpt276.jade.carbontracker.model.Transportation;
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
    private List<Bill> billsElec;
    private List<Bill> billsGas;
    private final int NUM_ENTRIES = journeyCollection.countJourneys();
    private DatePickerDialog dialog;

    private int mode = 0;
    private int dateMode = 1;

    private Calendar calendar = Calendar.getInstance();
    private Date dateSelected = calendar.getTime();
    private Date dateStart = null;
    private Date dateEnd = calendar.getTime();

    private String emissionDate[] = new String[NUM_ENTRIES];
    private String emissionRouteNames[] = new String[NUM_ENTRIES];
    private float emissionValues[] = new float[NUM_ENTRIES];
    private double emissionDistance[] = new double[NUM_ENTRIES];
    private String emissionVehicleNames[] = new String[NUM_ENTRIES];


    private static final String TAG = "CarbonFootPrintActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.carbonfootprintactivitytoolbarhint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_carbon_footprint);

        setupDates();
        Log.i("spinner", "dateEnd = " + dateEnd.toString());

        setupDatePicker();
        loadData();
        setupButton();
        setupSpinner();
    }

    private void setupDates() {
        dateSelected = new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dateEnd = new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setupDatePicker() {
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            //dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateChangedListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.clear();
                c.set(year, month, dayOfMonth);
                c.set(Calendar.HOUR_OF_DAY, 0);
                dateSelected = c.getTime();
                Log.i("datePicker", "year = " + year);
                Log.i("datePicker", "dateSelected = " + dateSelected.toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private Date currentDate() {
        Calendar c = Calendar.getInstance();
        return new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    private void setupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_date);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.label_date_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelected(false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Calendar calendar = Calendar.getInstance();
                Date date;

                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                switch (position) {
                    case 0:
                        dateMode = 1;
                        dialog.show();
                        setupTips();
                        break;
                    case 1:
                        dateMode = 2;
                        date = calendar.getTime();
                        Log.i("spinner", "dateEnd = " + dateEnd.toString());
                        calendar.setTime(date);
                        calendar.add(Calendar.DAY_OF_MONTH, -28);
                        date = calendar.getTime();
                        dateStart = date;
                        Log.i("spinner", "dateStart = " + dateStart.toString());
                        break;
                    case 2:
                        dateMode = 2;
                        date = calendar.getTime();
                        calendar.setTime(date);
                        calendar.add(Calendar.YEAR, -1);
                        date = calendar.getTime();
                        dateStart = date;
                        break;
                }

                setupPieChart();
                setupBarChart();
                setupTable();

                barChart.setVisibility(View.INVISIBLE);
                table.setVisibility(View.INVISIBLE);
                pieChart.setVisibility(View.VISIBLE);
                mode = 0;
                pieChart.invalidate();
                barChart.invalidate();
                table.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }


    Tip tip = new Tip();

    private void setupTips() {
        final TextView tv = (TextView) findViewById(R.id.footprint_tips);

        Emission emission = Emission.getInstance();
        JourneyCollection jc = emission.getJourneyCollection();

        double CarEmissions = 0;
        double BusEmission = 0;
        double SkyTrainEmission = 0;
        double Walk = 0;
        double Bike = 0;//

        // this adds all emissions
        for (Journey j : jc.getJourneyList()) {
            Log.i(TAG, "setupTips: Journey:" + j.getDateObj().toString());
            Log.i(TAG, "setupTips: Current:" + dateSelected.toString());

            if (Graph.compareDates(j.getDateObj(), dateSelected) == 0) {
                Transport mode = j.getTransType().getTransMode();
                switch (mode) {
                    case CAR:
                        CarEmissions += j.getTotalTravelledEmissions();
                        break;
                    case BIKE:
                        Bike += j.getRoute().getOtherDistance();
                        break;
                    case WALK:
                        Walk += j.getRoute().getOtherDistance();
                        break;
                    case BUS:
                        BusEmission += j.getTotalTravelledEmissions();
                        break;
                    case SKYTRAIN:
                        SkyTrainEmission += j.getTotalTravelledEmissions();
                        break;
                }
            }
        }
        tip.setTotalCarEmissions(CarEmissions);
        tip.setTotalBusEmission(BusEmission);
        tip.setTotalSkyTrainEmission(SkyTrainEmission);

        tip.setTotalWalk(Walk);
        tip.setTotalBike(Bike);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tip1 = tip.getJourneyTip();
                tv.setText(tip1);

            }
        });
    }

    private void setupTable() {
        table = (TableLayout) findViewById(R.id.tableFootprint);
        table.removeAllViews();
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
                        tv.setTextSize(10f);
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
                        tv.setTextSize(10f);
                        break;
                    case 1:
                        tv.setText(Emission.DATE_FORMAT.format(b.getEndDate()));
                        tv.setTextSize(10f);
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
                        tv.setTextSize(11f);
                        break;
                    case 1:
                        tv.setText(Emission.DATE_FORMAT.format(b.getEndDate()));
                        tv.setTextSize(11f);
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
        Transportation t;

        for (int i = 0; i < NUM_ENTRIES; ++i) {
            j = journeyCollection.getJourney(i);
            t = j.getTransType();

            if (j.getDateObj() != null)
                emissionDate[i] = Emission.DATE_FORMAT.format(j.getDateObj());
            else emissionDate[i] = "its fucked";
            emissionRouteNames[i] = j.getName();
            emissionDistance[i] = j.getRoute().getCityDistance() + j.getRoute().getCityDistance();
            if (t.getCar() != null) {
                emissionVehicleNames[i] = t.getCar().getNickName();
            } else if (t.getSkytrain() != null) {
                emissionVehicleNames[i] = t.getSkytrain().getNickName();
            } else if (t.getBus() != null) {
                emissionVehicleNames[i] = t.getBus().getNickName();
            } else {
                emissionVehicleNames[i] = "n/a";
            }
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
                switch (mode % 3) {
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
        PieData data = Graph.getPieData(getString(R.string.label_graph_title), dateMode,
                dateSelected, dateStart, dateEnd);
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
        BarData data = Graph.getBarData(getString(R.string.label_graph_title), dateMode,
                dateSelected, dateStart, dateEnd);
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

        XAxisValueFormatter(String[] labels) {
            this.labels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return labels[(int) value];
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}
