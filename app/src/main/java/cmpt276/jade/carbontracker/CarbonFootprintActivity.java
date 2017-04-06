package cmpt276.jade.carbontracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cmpt276.jade.carbontracker.enums.DateMode;
import cmpt276.jade.carbontracker.enums.GroupMode;
import cmpt276.jade.carbontracker.enums.MeasurementUnit;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.fragment.TipDialog;
import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Graph;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Settings;
import cmpt276.jade.carbontracker.model.Tip;
import cmpt276.jade.carbontracker.model.Transportation;
import cmpt276.jade.carbontracker.model.Utilities;

/*
 *  Displays user's carbon footprint in graphs or table
 */
public class CarbonFootprintActivity extends AppCompatActivity {

    private enum GraphMode {PIE, BAR, TABLE}

    private JourneyCollection journeyCollection = Emission.getInstance().getJourneyCollection();
    private Utilities utilities = Emission.getInstance().getUtilities();
    private PieChart pieChart;
    private CombinedChart barChart;
    private TableLayout table;
    private List<Bill> billsElec;
    private List<Bill> billsGas;
    private final int NUM_ENTRIES = journeyCollection.countJourneys();
    private DatePickerDialog dialog;
    private Spinner spinnerDate;
    private Spinner spinnerSort;

    private GraphMode graphMode = GraphMode.PIE;
    private DateMode dateMode = DateMode.SINGLE;
    private GroupMode groupMode = GroupMode.TRANSPORTATION;
    private Boolean sillyMode;

    private Calendar calendar = Calendar.getInstance();
    private Date dateSelected = calendar.getTime();
    private Date dateStart = null;
    private Date dateEnd = calendar.getTime();

    private String emissionDate[] = new String[NUM_ENTRIES];
    private String emissionRouteNames[] = new String[NUM_ENTRIES];
    private float emissionValues[] = new float[NUM_ENTRIES];
    private double emissionDistance[] = new double[NUM_ENTRIES];
    private String emissionVehicleNames[] = new String[NUM_ENTRIES];

    // 20.6 T CO2 / household (~2.5 people) / year -> Kg CO2 / day / person
    private final int CAN_AVG_EMISSIONS = (int) (20.6 * 1000000 / 360 / 2.5);
    private final int CAN_TARGET_EMISSIONS = (int) (CAN_AVG_EMISSIONS * 0.7);   // 30% reduction
    private final int CAN_AVG_EMISSIONS_SILLY =
            (int) Emission.getInstance().getSettings().calcTreeAbsorbtion(CAN_AVG_EMISSIONS);
    private final int CAN_TARGET_EMISSIONS_SILLY =
            (int) Emission.getInstance().getSettings().calcTreeAbsorbtion(CAN_TARGET_EMISSIONS);

    private static final String TAG = "CarbonFootPrintActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.carbonfootprintactivitytoolbarhint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_carbon_footprint);

        sillyMode = (Emission.getInstance().getSettings().getSillyMode() == MeasurementUnit.TREES);

        setupDates();
        Log.i("spinnerDate", "dateEnd = " + dateEnd.toString());

        setupDatePicker();
        loadData();
        setupButton();
        setupDateSpinner();
        setupSortSpinner();
    }

    private void setupDates() {
        dateSelected = currentDate();
        dateEnd = currentDate();
    }

    private void setupDatePicker() {
        dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.clear();
                c.set(year, month, dayOfMonth);
                c.set(Calendar.HOUR_OF_DAY, 0);
                dateSelected = c.getTime();
                Log.i("datePicker", "year = " + year);
                Log.i("datePicker", "dateSelected = " + dateSelected.toString());

                setupTips();
                setupPieChart();
                setupBarChart();
                setupTable();

                switchGraphs();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private Date currentDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    // mode = 0 : last month
    //        1 : last year
    private Date getPast(int mode) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        switch (mode) {
            case 0:
                c.add(Calendar.DAY_OF_YEAR, -28);
                break;
            case 1:
                c.add(Calendar.YEAR, -1);
                break;
        }

        return c.getTime();
    }

    private void switchGraphs() {
        int position = spinnerDate.getSelectedItemPosition();

        if (position == 0) {
            table.setVisibility(View.INVISIBLE);
            barChart.setVisibility(View.INVISIBLE);
            pieChart.setVisibility(View.VISIBLE);
            pieChart.invalidate();
            graphMode = GraphMode.PIE;
        } else {
            table.setVisibility(View.INVISIBLE);
            barChart.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.INVISIBLE);
            barChart.invalidate();
            graphMode = GraphMode.BAR;
        }
    }

    private void setupDateSpinner() {
        spinnerDate = (Spinner) findViewById(R.id.spinner_date);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.label_date_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(adapter);
        spinnerDate.setSelected(false);

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        dateMode = DateMode.SINGLE;
                        Log.i("spinnerDate","dateSelected = "+dateSelected.toString());
                        dialog.show();

                        break;
                    case 1:
                        dateMode = DateMode.RANGE;
                        Log.i("spinnerDate", "dateEnd = " + dateEnd.toString());
                        dateStart = getPast(0);
                        Log.i("spinnerDate", "dateStart = " + dateStart.toString());

                        break;
                    case 2:
                        dateMode = DateMode.RANGE;
                        Log.i("spinnerDate", "dateEnd = " + dateEnd.toString());
                        dateStart = getPast(1);
                        Log.i("spinnerDate", "dateStart = " + dateStart.toString());

                        break;
                }

                setupPieChart();
                setupBarChart();
                setupTable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    private void setupSortSpinner() {
        spinnerSort = (Spinner) findViewById(R.id.spinner_sort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.label_spinner_sort, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(adapter);
        spinnerSort.setSelected(false);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        groupMode = GroupMode.TRANSPORTATION;
                        break;
                    case 1:
                        groupMode = GroupMode.ROUTE;
                        break;
                }

                setupPieChart();
                setupBarChart();
                setupTable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }


    Tip tip = new Tip();

    private void setupTips() {
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

        final TipDialog d = new TipDialog();
        d.setTipDialogListener(new TipDialog.TipDialogListener() {
            @Override
            public void onNextClicked(TextView tv) {
                String tip1 = tip.getJourneyTip();
                tv.setText(tip1);
            }

            @Override
            public void onCloseClicked(TextView tv) {

            }
        });



        Button btnTips = (Button) findViewById(R.id.btn_tip);
        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.show(getSupportFragmentManager(),"Dialog");
            }
        });
    }

    private void setupTable() {
        Settings settings = Emission.getInstance().getSettings();
        table = (TableLayout) findViewById(R.id.tableFootprint);
        table.removeAllViews();
        table.setShrinkAllColumns(true);

        // Journeys
        TableRow labelRow = new TableRow(this);
        String[] labels;

        if (settings.getSillyMode() == MeasurementUnit.REGULAR)
            labels = getResources().getStringArray(R.array.label_table);
        else
            labels = getResources().getStringArray(R.array.label_table_silly);

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
        String[] labelsElec;

        if (sillyMode) {
            labelsElec = getResources().getStringArray(R.array.label_table_elec_silly);
        } else {
            labelsElec = getResources().getStringArray(R.array.label_table_elec);
        }

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
                        if (sillyMode) {
                            tv.setText(String.valueOf(Emission.round(
                                    settings.calcTreeAbsorbtion(b.getEmissionTotal()))));
                        } else {
                            tv.setText(String.valueOf(Emission.round(b.getEmissionTotal())));
                        }
                        break;
                    case 4:
                        if (sillyMode) {
                            tv.setText(String.valueOf(Emission.round(
                                    settings.calcTreeAbsorbtion(b.getEmissionAvg()))));
                        } else {
                            tv.setText(String.valueOf(Emission.round(b.getEmissionAvg())));
                        }
                        break;
                }
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tableRow.addView(tv);
            }
        }

        // Gas bills
        TableRow labelRowGas = new TableRow(this);
        String[] labelsGas;

        if (sillyMode) {
            labelsGas = getResources().getStringArray(R.array.label_table_gas_silly);
        } else {
            labelsGas = getResources().getStringArray(R.array.label_table_gas);
        }

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
                        if (sillyMode) {
                            tv.setText(String.valueOf(Emission.round(
                                    settings.calcTreeAbsorbtion(b.getEmissionTotal()))));
                        } else {
                            tv.setText(String.valueOf(Emission.round(b.getEmissionTotal())));
                        }
                        break;
                    case 4:
                        if (sillyMode) {
                            tv.setText(String.valueOf(Emission.round(
                                    settings.calcTreeAbsorbtion(b.getEmissionAvg()))));
                        } else {
                            tv.setText(String.valueOf(Emission.round(b.getEmissionAvg())));
                        }
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
        Settings settings = Emission.getInstance().getSettings();

        for (int i = 0; i < NUM_ENTRIES; ++i) {
            j = journeyCollection.getJourney(i);
            t = j.getTransType();

            if (j.getDateObj() != null) {
                emissionDate[i] = Emission.DATE_FORMAT.format(j.getDateObj());
            } else {
                emissionDate[i] = "n/a";
            }

            emissionRouteNames[i] = j.getName();
            emissionDistance[i] = j.getTotalDriven();

            if (t.getCar() != null) {
                emissionVehicleNames[i] = t.getCar().getNickName();
                if (!sillyMode) {
                    emissionValues[i] = (float) Emission.round(j.getTotalTravelledEmissions());
                } else {
                    emissionValues[i] = (float)
                            Emission.round(settings.calcTreeAbsorbtion(j.getTotalTravelledEmissions()));
                }
            } else if (t.getSkytrain() != null) {
                emissionVehicleNames[i] = t.getSkytrain().getNickName();
                if (!sillyMode) {
                    emissionValues[i] = (float) Emission.round(j.getSkytrainEmissions());
                } else {
                    emissionValues[i] = (float)
                            Emission.round(settings.calcTreeAbsorbtion(j.getSkytrainEmissions()));
                }
            } else if (t.getBus() != null) {
                emissionVehicleNames[i] = t.getBus().getNickName();
                if (!sillyMode) {
                    emissionValues[i] = (float) Emission.round(j.getBusEmissions());
                } else {
                    emissionValues[i] = (float)
                            Emission.round(settings.calcTreeAbsorbtion(j.getBusEmissions()));
                }
            } else {
                emissionVehicleNames[i] = "n/a";
                emissionValues[i] = 0;
            }
        }

        billsElec = utilities.getListBillElec();
        billsGas = utilities.getListBillGas();
    }

    private void setupButton() {
        Button btn = (Button) findViewById(R.id.btn_graph_toggle);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (graphMode) {
                    case PIE:
                        pieChart.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.VISIBLE);
                        barChart.invalidate();
                        graphMode = GraphMode.BAR;

                        break;
                    case BAR:
                        barChart.setVisibility(View.INVISIBLE);
                        table.setVisibility(View.VISIBLE);
                        table.invalidate();
                        graphMode = GraphMode.TABLE;

                        break;
                    case TABLE:
                        table.setVisibility(View.INVISIBLE);
                        pieChart.setVisibility(View.VISIBLE);
                        pieChart.invalidate();
                        graphMode = GraphMode.PIE;

                        break;
                }
            }
        });
    }

    private void setupPieChart() {
        PieData data = Graph.getPieData(getString(R.string.label_graph_title), dateMode, groupMode,
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
        CombinedData cData = new CombinedData();
        cData.setData(data);
        data.setValueTextSize(12f);

        ArrayList<ILineDataSet> lines = new ArrayList<>();
        ArrayList<Entry> lineEntries = new ArrayList<>();

        for (int i = 0; i < data.getEntryCount(); ++i) {
            if (sillyMode)
                lineEntries.add(new Entry(i, CAN_AVG_EMISSIONS_SILLY));
            else
                lineEntries.add(new Entry(i, CAN_AVG_EMISSIONS));
        }

        LineDataSet line = new LineDataSet(lineEntries, "test");
        line.setDrawCircles(false);
        line.setColor(Color.RED);
        lines.add(line);

        ArrayList<Entry> lineEntriesTgt = new ArrayList<>();

        for (int i = 0; i < data.getEntryCount(); ++i) {
            if (sillyMode)
                lineEntriesTgt.add(new Entry(i, CAN_TARGET_EMISSIONS_SILLY));
            else
                lineEntriesTgt.add(new Entry(i, CAN_TARGET_EMISSIONS));
        }

        LineDataSet lineTgt = new LineDataSet(lineEntriesTgt, "test2");
        lineTgt.setDrawCircles(false);
        lineTgt.setColor(Color.YELLOW);
        lines.add(lineTgt);
        cData.setData(new LineData(lines));

        barChart = (CombinedChart) findViewById(R.id.bar_graph);
        barChart.setData(cData);
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
            if (value > -1 && value < labels.length)
                return labels[(int) value];
            else
                return "";
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
