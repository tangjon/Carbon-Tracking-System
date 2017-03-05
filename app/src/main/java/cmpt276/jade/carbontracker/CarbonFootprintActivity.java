package cmpt276.jade.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class CarbonFootprintActivity extends AppCompatActivity {

    private PieChart pieChart;
    private BarChart barChart;
    private Boolean pieShown = true;
    private final float TEXT_SIZE = 12f;
    private final int ANIM_Y_DURATION = 600;

    //dummy data
    private float dummyCarbon[] = {37.1f, 312f, 12f, 94.8f};
    private String dummyName[] = {"Work", "Groceries", "Friends", "School"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint);

        setupPieChart();
        setupBarChart();
        setupButton();
    }

    private void setupButton() {
        Button btn = (Button) findViewById(R.id.btn_graph_toggle);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pieShown) {
                    pieChart.setVisibility(View.INVISIBLE);
                    pieChart.setEnabled(false);
                    barChart.setEnabled(true);
                    barChart.setVisibility(View.VISIBLE);
                    barChart.invalidate();
                    pieShown = false;
                } else {
                    pieChart.setEnabled(true);
                    pieChart.setVisibility(View.VISIBLE);
                    barChart.setVisibility(View.INVISIBLE);
                    barChart.setEnabled(false);
                    pieChart.invalidate();
                    pieShown = true;
                }
            }
        });
    }


    private void setupBarChart() {
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < dummyCarbon.length; ++i)
            barEntries.add(new BarEntry((float) i, dummyCarbon[i], dummyName[i]));

        BarDataSet dataSet = new BarDataSet(barEntries, getResources().getString(R.string.graph_title));
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        BarData data = new BarData(dataSet);
        data.setValueTextSize(TEXT_SIZE);
        data.setBarWidth(1f);

        barChart = (BarChart) findViewById(R.id.bar_graph);
        barChart.setData(data);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dummyName));

        Description desc = new Description();
        desc.setEnabled(false);
        barChart.setDescription(desc);
        barChart.animateY(ANIM_Y_DURATION);
        barChart.invalidate();
        barChart.setEnabled(false);
        barChart.setVisibility(View.INVISIBLE); // activity starts with pie chart shown
    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < dummyCarbon.length; ++i)
            pieEntries.add(new PieEntry(dummyCarbon[i], dummyName[i]));

        PieDataSet dataSet = new PieDataSet(pieEntries, getResources().getString(R.string.graph_title));
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(TEXT_SIZE);

        pieChart = (PieChart) findViewById(R.id.pie_graph);
        pieChart.setData(data);

        Description desc = new Description();
        desc.setEnabled(false);
        pieChart.setDescription(desc);
        pieChart.animateY(ANIM_Y_DURATION);
        pieChart.invalidate();
    }
}
