package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmpt276.jade.carbontracker.enums.MeasurementUnit;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.Route;

/**
 * Journey summary displays all the important info stored in the journey object
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_summary);
        getData();
        setData();
        setupBackBtn();

        hideSystemUI();
    }

    private void hideSystemUI() {
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_menu);
//        layout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideSystemUI();
//            }
//        });

        View mDecorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.

        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;

        mDecorView.setSystemUiVisibility(uiOptions);
    }

    private void getData() {
        journey = Emission.getInstance().getJourneyBuffer();
        car = journey.getTransType().getCar();
        route = journey.getRoute();
    }

    private void setData() {
        TextView transInfoLabel = (TextView) findViewById(R.id.textViewJourneySummaryTranportationInfo);
        MeasurementUnit sillyMode = Emission.getInstance().getSettings().getSillyMode();
        //Car
        if (journey.getTransType().getTransMode().equals(Transport.CAR)) {
            transInfoLabel.setText("Car Info");
            // Display Journey Info
            TextView journeyInfo = (TextView) findViewById(R.id.textJourneyInfo);
            journeyInfo.setText(getString(R.string.journey_info, journey.getName(), journey.getDate()));
            // Display Route Info
            TextView routeInfo = (TextView) findViewById(R.id.textRouteInfo);
            routeInfo.setText(getString(R.string.route_info,
                    journey.getName(),
                    route.getCityDistance(),
                    route.getHighWayDistance(),
                    journey.getTotalDriven()));
            // Display Car Info
            TextView carInfo = (TextView) findViewById(R.id.textCarInfo);
            carInfo.setText(getString(R.string.car_info, car.getNickName(),
                    car.getMake(),
                    car.getModel(),
                    car.getYear(),
                    car.getFuelType()));
            // Display Emission Info
            TextView hWayDrive = (TextView) findViewById(R.id.textEmissionsInfo);
            if(sillyMode == MeasurementUnit.REGULAR) {
                hWayDrive.setText(getString(R.string.emission_info,
                        journey.getTotalHighway(),
                        journey.getTotalCity(),
                        journey.getTotalTravelledEmissions()

                ));
            }
            else if(sillyMode == MeasurementUnit.TREES){
                Double treeAbsorbtion = Emission.getInstance().getSettings().calcTreeAbsorbtion(journey.getTotalTravelledEmissions());
                hWayDrive.setText("Your trip will take " + String.format("%.0f" , treeAbsorbtion) +
                        " trees one hour to remove the carbon you produced.");
            }
        }

        //Bus
        else if (journey.getTransType().getTransMode().equals(Transport.BUS)) {
            transInfoLabel.setText("Bus Info");
            // Display Journey Info
            TextView journeyInfo = (TextView) findViewById(R.id.textJourneyInfo);
            journeyInfo.setText(getString(R.string.journey_info, journey.getName(), journey.getDate()));
            // Display Route Info
            TextView routeInfo = (TextView) findViewById(R.id.textRouteInfo);
            routeInfo.setText(journey.getRoute().getName() + "\nTotal Distance: " + journey.getRoute().getOtherDistance());
            //Display Bus Info
            TextView busInfo = (TextView) findViewById(R.id.textCarInfo);
            busInfo.setText(journey.getTransType().getBus().getNickName() + "\nRoute #: " +
                    journey.getTransType().getBus().getRouteNumber());
            //Display Emission Info
            TextView emissions = (TextView) findViewById(R.id.textEmissionsInfo);
            if(sillyMode == MeasurementUnit.REGULAR) {
                emissions.setText("Carbon Emission (Kg): " + String.format("%.2f", journey.getBusEmissions()));
            }
            else if(sillyMode == MeasurementUnit.TREES){
                double treeAbsorbtion = Emission.getInstance().getSettings().calcTreeAbsorbtion(journey.getBusEmissions());
                emissions.setText("Your trip will take " + String.format("%.0f" , treeAbsorbtion) +
                        " trees one hour to remove the carbon you produced.");
            }

        } else if (journey.getTransType().getTransMode().equals(Transport.SKYTRAIN)) {
            transInfoLabel.setText("Skytrain Info");
            // Display Journey Info
            TextView journeyInfo = (TextView) findViewById(R.id.textJourneyInfo);
            journeyInfo.setText(getString(R.string.journey_info, journey.getName(), journey.getDate()));
            // Display Route Info
            TextView routeInfo = (TextView) findViewById(R.id.textRouteInfo);
            routeInfo.setText(journey.getRoute().getName() + "\nTotal Distance: " + journey.getRoute().getOtherDistance());
            //Display Skytrain Info
            TextView trainInfo = (TextView) findViewById(R.id.textCarInfo);
            trainInfo.setText(journey.getTransType().getSkytrain().getNickName() + "\nLine Name: " +
                    journey.getTransType().getSkytrain().getSkytrainLine() + "\nBoarding Station: " +
                    journey.getTransType().getSkytrain().getBoardingStation());
            //Display Emission Info
            TextView emissions = (TextView) findViewById(R.id.textEmissionsInfo);
            if(sillyMode == MeasurementUnit.REGULAR) {
                emissions.setText("Carbon Emission (Kg): " + String.format("%.2f", journey.getSkytrainEmissions()));
            }
            else if(sillyMode == MeasurementUnit.TREES){
                Double treeAbsorbtion = Emission.getInstance().getSettings().calcTreeAbsorbtion(journey.getSkytrainEmissions());
                emissions.setText("Your trip will take " + String.format("%.0f" , treeAbsorbtion) +
                        " trees one hour to remove the carbon you produced.");
            }


        } else if (journey.getTransType().getTransMode().equals(Transport.WALK)) {
            transInfoLabel.setText("Walk Info");
            TextView journeyInfo = (TextView) findViewById(R.id.textJourneyInfo);
            journeyInfo.setText(getString(R.string.journey_info, journey.getName(), journey.getDate()));
            // Display Route Info
            TextView routeInfo = (TextView) findViewById(R.id.textRouteInfo);
            routeInfo.setText(journey.getRoute().getName() + "\nTotal Distance: " + journey.getRoute().getOtherDistance());
            //Display Walk Info
            TextView trainInfo = (TextView) findViewById(R.id.textCarInfo);
            trainInfo.setText("Congratulations on making no carbon impact!");
            //Display Emission Info
            TextView emissions = (TextView) findViewById(R.id.textEmissionsInfo);

            if(sillyMode == MeasurementUnit.REGULAR) {
                emissions.setText("Carbon Emission (Kg): " + String.valueOf(0));
            }
             else if(sillyMode == MeasurementUnit.TREES){
                emissions.setText("Your trip created no carbon and allowed trees to take a break!");
            }

        } else if (journey.getTransType().getTransMode().equals(Transport.BIKE)) {
            transInfoLabel.setText("Bike Info");
            TextView journeyInfo = (TextView) findViewById(R.id.textJourneyInfo);
            journeyInfo.setText(getString(R.string.journey_info, journey.getName(), journey.getDate()));
            // Display Route Info
            TextView routeInfo = (TextView) findViewById(R.id.textRouteInfo);
            routeInfo.setText(journey.getRoute().getName() + "\nTotal Distance: " + journey.getRoute().getOtherDistance());
            //Display Walk Info
            TextView trainInfo = (TextView) findViewById(R.id.textCarInfo);
            trainInfo.setText("Congratulations on making no carbon impact!");
            //Display Emission Info
            TextView emissions = (TextView) findViewById(R.id.textEmissionsInfo);
            if(sillyMode == MeasurementUnit.REGULAR) {
                emissions.setText("Carbon Emission (Kg): " + String.valueOf(0));
            }
            else if(sillyMode == MeasurementUnit.TREES){
                emissions.setText("Your trip created no carbon and allowed trees to take a break!");
            }

        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


}
