package cmpt276.jade.carbontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import cmpt276.jade.carbontracker.adapter.CarListAdapter;
import cmpt276.jade.carbontracker.model.CarCollection;

public class CarListActivity extends AppCompatActivity {

    //TODO TEMPORARY CAR COLLECTION STATIC SHOULD PLACED IN LOG CLASS
    public static CarCollection globCollection = new CarCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        setUpAddButton(R.id.btn_add_car);

        //TODO String Array Argument to be implemented
        updateUI();


    }

    private void updateUI() {
        List<String> stringList = globCollection.makeToStringList();

        // Link widget
        ListView lstView = (ListView) findViewById(R.id.lv_carList);
        // Create an ArrayAdapter using the string array and a default spinner layout
        CarListAdapter adapter = new CarListAdapter(CarListActivity.this, globCollection.toList());
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, stringList);
        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        lstView.setAdapter(adapter);
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
