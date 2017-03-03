package cmpt276.jade.carbontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import cmpt276.jade.carbontracker.adapter.CarListAdapter;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;

public class CarListActivity extends AppCompatActivity {

    //TODO TEMPORARY CAR COLLECTION STATIC SHOULD PLACED IN LOG CLASS
    public static CarCollection globCollection = new CarCollection();

    private ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        setUpAddButton(R.id.btn_add_car);

        updateUI();


    }

    private void setUpEditMode() {
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Fetch Car Object
                Car car = (Car) parent.getAdapter().getItem(position);


            }
        });
    }

    private void updateUI() {
        List<String> stringList = globCollection.makeToStringList();


        setUpListView();
    }

    private void setUpListView() {
        // Link widget
        lstView = (ListView) findViewById(R.id.lv_carList);
        // Create an ArrayAdapter using the string array and a default spinner layout
        CarListAdapter adapter = new CarListAdapter(CarListActivity.this, globCollection.toList());
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, stringList);
        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        lstView.setAdapter(adapter);

        // TODO Implement Edit Mode/Delete
        setUpEditMode();
    }

    private void setUpAddButton(int btnID) {
        Button button = (Button) findViewById(btnID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarInfoActivity.getIntentFromActivity(CarListActivity.this);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
}
