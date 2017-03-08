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
    public static JourneyCollection listOfJourneys = Emission.getInstance().getJourneyCollection();
    private Journey intentJourney;
    private int Mode = 0;

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
        setupDeletebtn();
        populateList();
        checkFootprintBtn();
    }

    private void checkFootprintBtn() {
        Button button = (Button) findViewById(R.id.btnViewFootprint);
        if (listOfJourneys.countJourneys() == 0) button.setEnabled(false);
        else button.setEnabled(true);
    }

    private void setupDeletebtn() {
        final Button button = (Button) findViewById(R.id.btnMode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mode == 1){
                    Mode = 0;
                    setupClickJourneyList();
                    button.setText("Delete");
                }
                else if(Mode == 0){
                    Mode = 1;
                    toggleDeleteMode();
                    button.setText("Edit");
                }

            }
        });
    }


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
                Journey journey = new Journey("TEMP", car, route);
                Intent intent = CarListActivity.getIntentFromActivity(JourneyListActivity.this);
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
                journey.setPosition(position);
                journey.setMode(1);
                intent.putExtra("Journey", journey);
                startActivity(intent);
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
        if(journey != null) {
            if (journey.getMode() == 0) {
                this.intentJourney = journey;
                listOfJourneys.addJourney(journey);
                Emission.getInstance().setJourneyCollection(listOfJourneys);
            } else if (journey.getMode() == 1) {
                this.intentJourney = journey;
                listOfJourneys.editJourney(intentJourney, intentJourney.getPosition());
                Emission.getInstance().setJourneyCollection(listOfJourneys);
            }
        }
    }

    private void toggleDeleteMode() {
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
                listOfJourneys.deleteJourney(position);
                Emission.getInstance().setJourneyCollection(listOfJourneys);
                populateList();
                return true;
            }
        });
    }

}
