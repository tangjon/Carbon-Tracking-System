package cmpt276.jade.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.utils.CarManager;

public class CarInfoActivity extends AppCompatActivity {
    // Field to contain all car info from vehicle.csv
    private Car[] carList;

    // String of Manufactures
    private List<String> makeList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_info);
        // LOAD
        loadCarList();

        populateMakeList();

        // Spinner for Manufacture
        setUpSpinner(makeList, R.id.spn_make);
    }

    // This Highly InEfficient
    private void populateMakeList(){
        for (Car car:carList) {
            if(!makeList.contains(car.getMake())){
                makeList.add(car.getMake());
            }
        }

    }

    private void setUpSpinner(List<String> stringArray, int spnID) {
        Spinner spinner = (Spinner) findViewById(spnID);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, stringArray);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void loadCarList() {
        carList = CarManager.readCarData(this,R.raw.vehicle_trimmed);
    }



}
