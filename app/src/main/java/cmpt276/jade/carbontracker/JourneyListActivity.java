package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import cmpt276.jade.carbontracker.adapter.RouteListAdapter;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;

public class JourneyListActivity extends AppCompatActivity {
    public static JourneyCollection listOfJourneys = new JourneyCollection();
    private Journey intentJourney;

    public static Intent getJourneyListIntent(Context context) {
        return new Intent(context, JourneyListActivity.class);

    }

    //maybe have a get data from intent that can handle a journey being passed into it?
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);


        setupAddBtn();
        setupFootprintBtn();
        getIntentData();
        setupClickJourneyList();
        populateList();
        checkFootprintBtn();
    }

    private void checkFootprintBtn() {
        Button button = (Button) findViewById(R.id.btnViewFootprint);
        if (listOfJourneys.countJourneys() == 0) button.setEnabled(false);
        else button.setEnabled(true);
    }

    //TODO Delete from list

    private void setupFootprintBtn() {
        Button button = (Button) findViewById(R.id.btnViewFootprint);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarbonFootprintActivity.getIntent(JourneyListActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupAddBtn() {
        Button button = (Button) findViewById(R.id.btnAddJourney);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car car = new Car();
                Route route = new Route("TEMP ROUTE NAME AND DATA", -1, -1);
                Journey journey = new Journey("TEMP JOURNEY NAME", car, route);
                //TODO
                //Very likely issue when going back/cancelling new entry move add journey to list to a get data method

                Intent intent = TransportSelectActivity.getTransportIntent(JourneyListActivity.this);
                intent.putExtra("Journey", journey);
                startActivity(intent);
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
        ListAdapter bucky=new RouteListAdapter(this,listOfJourneys.getJourneyDetails());
        ListView list = (ListView) findViewById(R.id.listviewJourney);
        list.setAdapter(bucky);
    }

    public void getIntentData() {
        Intent intent = getIntent();

        Journey journey = (Journey)intent.getSerializableExtra("Journey");
        if(journey != null){
            this.intentJourney = journey;
            listOfJourneys.addJourney(journey);
            Emission.getInstance().setJourneyCollection(listOfJourneys);
        }


    }
}
