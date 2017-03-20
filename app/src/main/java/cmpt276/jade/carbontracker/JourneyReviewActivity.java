package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        TextView carInfo = (TextView) findViewById(R.id.txtCarInfo);
        carInfo.setText(storedJourney.getTransType().getCar().getNickName() + "\nMake: " + storedJourney.getTransType().getCar().getMake() + "\nModel: " + storedJourney.getTransType().getCar().getModel() + "\nYear: " + storedJourney.getTransType().getCar().getYear());
        TextView routeInfo = (TextView) findViewById(R.id.txtRouteInfo);
        routeInfo.setText(storedJourney.getRoute().getName() + "\nCity Distance: "+ storedJourney.getRoute().getCityDistance() + "\nHighway Distance : " + storedJourney.getRoute().getHighWayDistance());
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
                String[] dateCheck = inputDate.getText().toString().trim().split("/", 3);
                int month = 0;
                int day = 0;
                int year = 0;
                if(dateCheck.length == 3) {
                    month = Integer.parseInt(dateCheck[1]);
                    day = Integer.parseInt(dateCheck[0]);
                    year = Integer.parseInt(dateCheck[2]);
                }
                else {
                    inputDate.setError("Please Enter a valid date");
                }
                if (inputName.getText().toString().trim().length() == 0) {
                    inputName.setError("Please Enter a nickname");
                }
                else if(month < 1 || month > 12 || day < 1 || day > 31 || year < 1900 || year > 9999){
                     inputDate.setError("Please Enter a valid date");
                }
                else {

                    storedJourney.setPosition(journey.getPosition());
                    storedJourney.setMode(journey.getMode());
                    storedJourney.setDate(inputDate.getText().toString().trim());
                    storedJourney.setName(inputName.getText().toString().trim());
                    Emission.getInstance().setJourneyBuffer(storedJourney);



                        if (journey.getMode() == 0) {
                            JourneyCollection listOfJourneys = Emission.getInstance().getJourneyCollection();
                            listOfJourneys.addJourney(storedJourney);
                            Emission.getInstance().setJourneyCollection(listOfJourneys);
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
