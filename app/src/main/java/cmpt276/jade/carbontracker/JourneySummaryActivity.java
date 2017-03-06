package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.Route;

public class JourneySummaryActivity extends AppCompatActivity {


    private Journey journey;
    private Car car;
    private Route route;

    public static Intent getJourneySummaryIntent(Context context) {
        Intent intent = new Intent(context, JourneySummaryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_summary);
        getData();
        setData();
        setupBackBtn();

    }

    private void getData() {
        Intent intent = getIntent();
        this.journey = (Journey)intent.getSerializableExtra("Journey");
        this.car = journey.getCar();
        this.route = journey.getRoute();
    }

    private void setData() {
        TextView carName = (TextView) findViewById(R.id.textCarName);
        carName.setText(car.getNickname());
        TextView routeName = (TextView) findViewById(R.id.textRouteName);
        routeName.setText(route.getName());
        TextView cityDrive = (TextView) findViewById(R.id.textCitydrv);
        cityDrive.setText(""+ String.format("%.2f",journey.getTotalCity()));
        TextView hwayDrive = (TextView) findViewById(R.id.textHwaydrv);
        hwayDrive.setText(""+ String.format("%.2f",journey.getTotalHighway()));
        TextView totalDrive = (TextView) findViewById(R.id.textTotaldrv);
        totalDrive.setText("" + String.format("%.2f",journey.getTotalTravelled()));

    }

    private void setupBackBtn() {
        Button btn = (Button) findViewById(R.id.btnJourneySummaryBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
