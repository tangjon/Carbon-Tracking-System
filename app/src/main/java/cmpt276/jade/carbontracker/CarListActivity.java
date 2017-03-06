package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import cmpt276.jade.carbontracker.adapter.CarListAdapter;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.utils.Mode;

public class CarListActivity extends AppCompatActivity {
    // Field for Recent Car List
    public static CarCollection recentCarList = new CarCollection();

    // Car List Activity Static Resource Fields
    public static String CAR_KEY = "carKey";

    // Car List Activity Resource Fields
    private String activity_name = "CarListActivity";
    private ListView lstView;

    public static Intent getIntentFromActivity(Context context) {
        Intent intent = new Intent(context, CarListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(activity_name);
        setContentView(R.layout.activity_car_list);

        setUpAddButton(R.id.btn_add_car);

        // Refresh the Car List
        updateUI();

    }

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

        // React to Long Click (EDIT MODE)
        // Send CarInfoActivity existing data about Car for Editting
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

        // React to Car Object Click, send to CarListActivity
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = Route_List_Activity.IntentForRouteList(CarListActivity.this);
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
                startActivity(intent);
            }
        });
    }

    // Refresh UI upon returning to activity
    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
}
