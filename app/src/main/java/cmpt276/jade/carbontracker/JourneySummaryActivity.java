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
/**
 *Journey summary displays all the important info stored in the journey object
 */
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
        getSupportActionBar().setTitle(getString(R.string.JourneySummaryHint));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_summary);
        getData();
        setData();
        setupBackBtn();

    }

    private void getData() {
        Intent intent = getIntent();
        journey = (Journey)intent.getSerializableExtra("Journey");
        car = journey.getCar();
        route = journey.getRoute();
    }

    private void setData() {
        TextView journeyInfo = (TextView) findViewById(R.id.textJourneyInfo);
        journeyInfo.setText(getString(R.string.label_name)+journey.getName() + getString(R.string.label_date) + journey.getDate());
        TextView routeInfo = (TextView) findViewById(R.id.textRouteInfo);
        routeInfo.setText(
                getString(R.string.label_name) +  " " + route.getName() +
                "\n" + getString(R.string.journey_summary_total_city_distance) + " "+ route.getCityDistance()+
                "\n" + getString(R.string.journey_summary_total_highway_distance) + " "+route.getHighWayDistance() +
                "\n" + getString(R.string.journey_summary_total_distance) + " " + journey.getTotalDriven());

        TextView carInfo = (TextView) findViewById(R.id.textCarInfo);
        carInfo.setText(getString(
                R.string.label_name) + car.getNickName() +
                "\n" + getString(R.string.label_make) + " " + car.getMake() +
                "\n" + getString(R.string.label_model) + " " + car.getModel()+
                "\n" + getString(R.string.label_year) + " "+ car.getYear() +
                "\n" + getString(R.string.label_fuel_type) + " " + car.getFuelType());
        TextView hwayDrive = (TextView) findViewById(R.id.textEmissionsInfo);
        hwayDrive.setText(getString(R.string.total_city_high_emission) + " "+ String.format("%.2f",journey.getTotalHighway()) +
                "\n" + getString(R.string.total_city_emission) + " "+ String.format("%.2f",journey.getTotalCity()) +
                "\n" + getString(R.string.total_emission)+ " " + String.format("%.2f",journey.getTotalTravelledEmissions()));


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
