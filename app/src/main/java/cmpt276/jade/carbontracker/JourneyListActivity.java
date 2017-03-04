package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;

public class JourneyListActivity extends AppCompatActivity {
    public static JourneyCollection listOfJourneys = new JourneyCollection();
    //maybe have a get data from intent that can handle a journey being passed into it?
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);

        setupAddBtn();
        setupClickJourneyList();
        populateList();

    }

    public static Intent getJourneyListIntent(Context context) {
        return new Intent(context, JourneyListActivity.class);

    }

    private void setupAddBtn() {
        Button button = (Button) findViewById(R.id.btnAddJourney);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car car = new Car();
                Route route = new Route();
                Journey journey = new Journey("A new journey", car, route);
                listOfJourneys.addJourney(journey);
                populateList();
            /*    Intent intent = CarListActivity.getIntentFromActivity(JourneyListActivity.this);
                startActivity(intent);*/
            }
        });

    }

    private void setupClickJourneyList() {
        //goto Journey Summary - short click
        ListView list = (ListView) findViewById(R.id.listviewJourney);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = JourneySummaryActivity.getJourneySummaryIntent(JourneyListActivity.this);
                Journey journey = listOfJourneys.getJourney(position);
                intent.putExtra("Journey", journey);
                startActivity(intent);
            }
        });


        //edit - long click
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = CarListActivity.getIntentFromActivity(JourneyListActivity.this);
                Journey journey = listOfJourneys.getJourney(position);
                listOfJourneys.editJourney(journey, position);
                intent.putExtra("Journey", journey);
                intent.putExtra("Journey", journey.getCar());
                intent.putExtra("Journey", journey.getRoute());
                startActivity(intent);
                finish();
                return true;
            }
        });
    }

    private void populateList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.journey_list, listOfJourneys.getJourneyName());
        ListView list = (ListView) findViewById(R.id.listviewJourney);
        list.setAdapter(adapter);
    }

}
