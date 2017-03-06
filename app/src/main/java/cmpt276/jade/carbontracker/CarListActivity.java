package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import cmpt276.jade.carbontracker.adapter.CarListAdapter;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.utils.Mode;

public class CarListActivity extends AppCompatActivity {

    //TODO TEMPORARY CAR COLLECTION STATIC SHOULD PLACED IN LOG CLASS
    public static CarCollection recentCarList = new CarCollection();
    public static String CAR_KEY = "carKey";

    private String activity_name = "CarListActivity";
    private ListView lstView;
    private Journey journey;
    private String test;

    public static Intent getIntentFromActivity(Context context) {
        Intent intent = new Intent(context, CarListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(activity_name);
        setContentView(R.layout.activity_car_list);
        getJourneyData();
        setUpAddButton(R.id.btn_add_car);

        setUpListView();


    }
/*
    private void setUpEditMode() {
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Fetch Car Object
                Car car = (Car) parent.getAdapter().getItem(position);
            }
        });

        lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Car car = (Car) parent.getAdapter().getItem(position);
                Intent intent = CarInfoActivity.getIntentFromActivity(CarListActivity.this, Mode.EDIT);
                intent.putExtra(CAR_KEY, car.getKEY().toString());
                startActivity(intent);
                return true;
            }
        });
    }
*/
    private void updateUI() {
        setUpListView();
    }

    private void setUpListView() {
        // Link widget
        lstView = (ListView) findViewById(R.id.lv_carList);
        // Create an ArrayAdapter using the string array and a default spinner layout
        CarListAdapter adapter = new CarListAdapter(CarListActivity.this, recentCarList.toList());
        // Apply the adapter to the spinner
        lstView.setAdapter(adapter);

        // TODO Implement Edit Mode/Delete
      //  setUpEditMode();

        // React to Car Object Click
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Sean- adding my journey object to your intent
                Car car = (Car) parent.getAdapter().getItem(position);
                journey.setCar(car);
                Intent intent = Route_List_Activity.IntentForRouteList(CarListActivity.this);
                intent.putExtra("Journey", journey);
                startActivity(intent);
            }
        });
    }

    private void setUpAddButton(int btnID) {
        Button button = (Button) findViewById(btnID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO SHOULD SEND US TO ROUTE
                // ACTIVITES
                Intent intent = CarInfoActivity.getIntentFromActivity(CarListActivity.this, Mode.ADD);
                intent.putExtra("Journey", journey);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
    //Sean - Gets journey object passed by journey list
    public void getJourneyData() {
        Intent intent = getIntent();
            journey = (Journey)intent.getSerializableExtra("Journey");

    }
}
