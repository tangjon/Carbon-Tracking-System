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

        // Spinner for Manufacture
//        setUpMakeSpinner(R.id.spn_make);

        setUpSpinner(R.id.spn_make);


    }
    private void setUpSpinner(int spnID){
        final Spinner spinner = (Spinner) findViewById(spnID);
        ArrayAdapter<String> adapter = null;


        switch (spnID){
            case R.id.spn_make:
                populateMakeList();
                // Create an ArrayAdapter using the string array and a default spinner layout
                adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, makeList);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        modelList.clear();
                        selectMake = (String) spinner.getSelectedItem();
                        setUpSpinner(R.id.spn_model);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                break;
            case R.id.spn_model:
                populateModelList(selectMake);
                // Create an ArrayAdapter using the string array and a default spinner layout
                adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, modelList);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        yearList.clear();
                        selectModel = (String) spinner.getSelectedItem();
                        setUpSpinner(R.id.spn_year);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case R.id.spn_year:
                populateYearList();
                // Create an ArrayAdapter using the string array and a default spinner layout
                adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, yearList);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        yearList.clear();
                        selectYear = (String) spinner.getSelectedItem();
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            default:
                break;
        }





        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void populateYearList() {
        for (Car car: carList ) {
            if(car.getMake().equals(selectMake) && car.getModel().equals(selectModel)){
                yearList.add(Integer.toString(car.getYear()));
            }
        }
        Toast.makeText(this, "" + yearList.toString(), Toast.LENGTH_SHORT).show();
    }


    private void setUpMakeSpinner(int spnID) {
        populateMakeList();
        final Spinner spinner = (Spinner) findViewById(spnID);
        // Listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                modelList.clear();
                selectMake = (String) spinner.getSelectedItem();
                Toast.makeText(CarInfoActivity.this, "" + selectMake, Toast.LENGTH_SHORT).show();
                setModelSpinner(R.id.spn_model, makeList.get(pos));
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, makeList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    // This Highly InEfficient
    private void populateMakeList(){
        for (Car car:carList) {
            if(!makeList.contains(car.getMake())){
                makeList.add(car.getMake());
            }
        }
    }
    private void setModelSpinner(int spnID, String make){
        populateModelList(make);
        Spinner spinner = (Spinner) findViewById(spnID);
        // Listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, modelList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    private void populateModelList(String make){
        for (Car car:
             carList) {
            if(car.getMake().equals(make) && !modelList.contains(car.getModel())){
                modelList.add(car.getModel());
            }
        }

    }

    private void loadCarList() {
        carList = CarManager.readCarData(this,R.raw.vehicle_trimmed);
    }



}
