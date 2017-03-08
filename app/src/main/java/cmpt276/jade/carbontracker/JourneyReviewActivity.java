package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.model.Journey;

public class JourneyReviewActivity extends AppCompatActivity {
    private Journey journey;
    private Journey intentJourney;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_review);

        getJourneyData();
        setupPage();
        setupDoneBtn();

    }

    private void setupPage() {
        TextView carInfo = (TextView) findViewById(R.id.txtCarInfo);
        carInfo.setText(intentJourney.getCar().getNickName() + "\n" + intentJourney.getCar().getMake() + "\n" + intentJourney.getCar().getModel() + "\n" + intentJourney.getCar().getYear());
        TextView routeInfo = (TextView) findViewById(R.id.txtRouteInfo);
        routeInfo.setText(intentJourney.getRoute().getName() + "\nCity Distance: "+ intentJourney.getRoute().getCityDistance() + "\nHighway Distance" + intentJourney.getRoute().getHighWayDistance());
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
//
    private void setupDoneBtn() {
        Button button = (Button) findViewById(R.id.btnFinish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputName = (EditText) findViewById(R.id.editJourneyName);
                EditText inputDate = (EditText) findViewById(R.id.editDate);

                if (inputName.getText().toString().trim().length() == 0) {
                    inputName.setError("Please Enter a nickname");
                }
                else {

                    intentJourney.setPosition(journey.getPosition());
                    intentJourney.setMode(journey.getMode());
                    intentJourney.setDate(inputDate.getText().toString().trim());
                    intentJourney.setName(inputName.getText().toString().trim());
                    Intent intent = JourneyListActivity.getJourneyListIntent(JourneyReviewActivity.this);
                    intent.putExtra("Journey", intentJourney);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

    }



    public void getJourneyData() {
        Intent intent = getIntent();
        journey = (Journey)intent.getSerializableExtra("Journey");
        intentJourney = new Journey(journey.getName(), journey.getCar(), journey.getRoute());
    }


}
