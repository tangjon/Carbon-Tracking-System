package cmpt276.jade.carbontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.utils.CarManager;

public class CarInfoActivity extends AppCompatActivity {
    // Field to contain all car info from vehicle.csv
    private Car[] carList;

    // String of Manufactures
    private List<String> makeList = new ArrayList<>();

    // String of Models for Specific Car
    private List<String> modelList = new ArrayList<>();

    // String of Years for Specific Car
    private List<String> yearList = new ArrayList<>();

    private String selectMake, selectModel, selectYear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_info);
        // LOAD
        loadCarList();

    }


    private void loadCarList() {
        carList = CarManager.readCarData(this,R.raw.vehicle_trimmed);
    }



}
