package cmpt276.jade.carbontracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;

/**
 *Journey review lets you review the data you entered for car and route and
 * will allow you to name the journey and the date
 */
public class JourneyReviewActivity extends AppCompatActivity {
    private Journey journey;
    private Journey storedJourney;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.JourneyReviewActivityHint));
        setContentView(R.layout.activity_journey_review);

        getJourneyData();
        setupPage();
        setupDoneBtn();
    }

    private void setupPage() {
        TextView transInfo = (TextView) findViewById(R.id.txtTransInfo);
        TextView transTag = (TextView) findViewById(R.id.textViewJourneyReviewTransportTag);
        TextView routeInfo = (TextView) findViewById(R.id.txtRouteInfo);
        if(Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.CAR)) {
            transTag.setText(R.string.car);
            transInfo.setText(storedJourney.getTransType().getCar().getNickName() + "\n" + getString(R.string.make) +
                    storedJourney.getTransType().getCar().getMake() + "\n" +getString(R.string.model) +
                    storedJourney.getTransType().getCar().getModel() + "\n" + getString(R.string.year) +
                    storedJourney.getTransType().getCar().getYear());


            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.city_distance) +
                    storedJourney.getRoute().getCityDistance() + "\n" + getString(R.string.highway_distance)
                    + storedJourney.getRoute().getHighWayDistance());
        }
        else if(Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.BUS)){
            transTag.setText(R.string.bus);
            transInfo.setText(storedJourney.getTransType().getBus().getNickName() + "\n" + getString(R.string.route_number) +
            storedJourney.getTransType().getBus().getRouteNumber());

            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.total_distance) + storedJourney.getRoute().getOtherDistance());

        }
        else if(Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.SKYTRAIN)){
            transTag.setText(R.string.Skytrain);
            transInfo.setText(storedJourney.getTransType().getSkytrain().getNickName() + "\n" + getString(R.string.line_name) +
            storedJourney.getTransType().getSkytrain().getSkytrainLine() + "\n" + getString(R.string.boarding_station) +
            storedJourney.getTransType().getSkytrain().getBoardingStation());

            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.total_distance) + storedJourney.getRoute().getOtherDistance());
        }
        else if(Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.WALK)){
            transTag.setText(R.string.walked);
            transInfo.setText(" ");

            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.total_distance) + storedJourney.getRoute().getOtherDistance());
        }
        else if(Emission.getInstance().getJourneyBuffer().getTransType().getTransMode().equals(Transport.BIKE)){
            transTag.setText(R.string.rode_bike);
            transInfo.setText(" ");

            routeInfo.setText(storedJourney.getRoute().getName() + "\n" + getString(R.string.total_distance) + storedJourney.getRoute().getOtherDistance());
        }
        if(journey.getMode() == 1){
            EditText inputName = (EditText) findViewById(R.id.editJourneyName);
            inputName.setText(journey.getName());
            EditText inputDate = (EditText) findViewById(R.id.editDate);
            inputDate.setText(journey.getDate());
        }
    }

    public static Intent getJourneyReviewIntent(Context context) {
        return new Intent(context, JourneyReviewActivity.class);

    }
    //Should go back to finish after checking name and date
    private void setupDoneBtn() {
        Button button = (Button) findViewById(R.id.btnFinish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputName = (EditText) findViewById(R.id.editJourneyName);
                EditText inputDate = (EditText) findViewById(R.id.editDate);
               // String date = inputDate.getText().toString();


                String[] dateCheck =inputDate.getText().toString().split("/", 3);

                int month = 0;
                int day = 0;
                int year = 0;
                if (dateCheck.length == 3) {
                    if(dateCheck[1].equals("") || dateCheck[0].equals("") || dateCheck[2].equals("")
                            || dateCheck[1].contains(".") || dateCheck[0].contains(".") || dateCheck[2].contains(".")
                            || dateCheck[1].contains("-") || dateCheck[0].contains("-") || dateCheck[2].contains("-")) {
                        inputDate.setError(getString(R.string.valid_date));
                    }
                    else {
                        month = Integer.parseInt(dateCheck[1]);
                        day = Integer.parseInt(dateCheck[0]);
                        year = Integer.parseInt(dateCheck[2]);
                    }
                } else {
                    inputDate.setError(getString(R.string.valid_date));
                }
                if (inputName.getText().toString().trim().length() == 0) {
                    inputName.setError(getString(R.string.valid_nickname));
                } else if (month < 1 || month > 12 || day < 1 || day > 31 || year < 1900 || year > 9999) {
                    inputDate.setError(getString(R.string.valid_date));
                } else if((month == 2 && day > 28 && year % 4 != 0) || (month == 2 && day > 29 && year % 4 == 0)){
                    inputDate.setError(getString(R.string.valid_date));
                } else if((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                    inputDate.setError(getString(R.string.valid_date));
                }else{


                    storedJourney.setPosition(journey.getPosition());
                    storedJourney.setMode(journey.getMode());
                    storedJourney.setDate(inputDate.getText().toString().trim());
                    //storedJourney.setDateObj(new Date(month+"/"+day+"/"+year));
                    storedJourney.setName(inputName.getText().toString().trim());
                    Emission.getInstance().setJourneyBuffer(storedJourney);


                    if (journey.getMode() == 0) {
//                        JourneyCollection listOfJourneys = Emission.getInstance().getJourneyCollection();
//                        listOfJourneys.addJourney(storedJourney);
//                        Emission.getInstance().setJourneyCollection(listOfJourneys);
                      Emission.getInstance().getJourneyCollection().addJourney(storedJourney);
                      DBAdapter.save(JourneyReviewActivity.this, storedJourney);
                    } else if (journey.getMode() == 1) {
                        JourneyCollection listOfJourneys = Emission.getInstance().getJourneyCollection();
                        listOfJourneys.editJourney(storedJourney, journey.getPosition());
                        Emission.getInstance().setJourneyCollection(listOfJourneys);
                    }


                    Intent intent = JourneyListActivity.getJourneyListIntent(JourneyReviewActivity.this);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    /*

                   This is not used anywhere is it needed?

                     */
                    // Journey journey = (Journey)intent.getSerializableExtra("Journey");

                }
            }
        });
    }





    public void getJourneyData() {
        journey = Emission.getInstance().getJourneyBuffer();
        storedJourney = new Journey(journey.getName(), journey.getTransType(), journey.getRoute());
    }


}
