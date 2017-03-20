package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Emission;
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
        journey = Emission.getInstance().getJourneyBuffer();
        car = journey.getTransType().getCar();
        route = journey.getRoute();
    }

    private void setData() {
        // Display Journey Info
        TextView journeyInfo = (TextView) findViewById(R.id.textJourneyInfo);
        journeyInfo.setText(getString(R.string.journey_info,journey.getName(),journey.getDate()));
        // Display Route Info
        TextView routeInfo = (TextView) findViewById(R.id.textRouteInfo);
        routeInfo.setText(getString(R.string.route_info,
                journey.getName(),
                route.getCityDistance(),
                route.getHighWayDistance(),
                journey.getTotalDriven()));
        // Display Car Info
        TextView carInfo = (TextView) findViewById(R.id.textCarInfo);
        carInfo.setText(getString(R.string.car_info,car.getNickName(),
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getFuelType()));
        // Display Emission Info
        TextView hWayDrive = (TextView) findViewById(R.id.textEmissionsInfo);
        hWayDrive.setText(getString(R.string.emission_info,
                journey.getTotalHighway(),
                journey.getTotalCity(),
                journey.getTotalTravelledEmissions()
        ));
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
