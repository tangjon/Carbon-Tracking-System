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
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cmpt276.jade.carbontracker.adapter.CustomSpinnerAdapter;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.utils.CarManager;
import cmpt276.jade.carbontracker.utils.Mode;

public class CarInfoActivity extends AppCompatActivity {
    // KEY FOR DETERMINING APP_MODE
    private static String APP_MODE = "mode";
    // Field to contain all car info from vehicle.csv
    private CarCollection carCollection;
    // String of Manufactures for spinner adapter
    private List<String> makeDisplayList = new ArrayList<>();
    // String of Models for Specific Car for spinner adapter
    private List<String> modelDisplayList = new ArrayList<>();


    // Helper Fields
    private String selectMake, selectModel, selectYear;
    // TAG
    private String TAG = "carinfoactivity";
    // Field to store the user selected car <----------- THIS IS OF INTEREST
    private Car userSelectedCar;

    public static Intent getIntentFromActivity(Context context, Mode mode) {
        Intent intent = new Intent(context, CarInfoActivity.class);
        intent.putExtra(APP_MODE, mode);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        // get mode from intent
        Mode mode = (Mode) getIntent().getExtras().getSerializable(APP_MODE);
        switch (mode) {
            case ADD:
                loadCarList();
                loadMakeDisplayList();
                setUpAllSpinners();
                break;
            case EDIT:
                // Fetch Select Car to Edit
                String key = getIntent().getExtras().getString(CarListActivity.CAR_KEY);
                userSelectedCar = CarListActivity.globCollection.getCarByKey(key);
                selectMake = userSelectedCar.getMake();
                selectModel = userSelectedCar.getModel();
                selectYear = Double.toString(userSelectedCar.getYear());
                loadCarList();
                loadMakeDisplayList();
                setUpAllSpinners();
                loadCurrentCarInfo();
                break;

        }
        // TODO Needs direct to next activity
        setUpNextBtn();
    }

    private void loadCurrentCarInfo() {
    }

    private void setUpNextBtn() {
        Button btn = (Button) findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarListActivity.globCollection.add(userSelectedCar);
            }
        });
    }

    private Spinner setUpSpinner(int spnID, List<String> stringList) {
        Spinner spinner = (Spinner) findViewById(spnID);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        return spinner;
    }

    private Spinner setUpCustomSpinner(int spnID, List<Car> carList) {
        final Spinner spinner = (Spinner) findViewById(spnID);
        // Create an ArrayAdapter using the string array and a default spinner layout
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(CarInfoActivity.this, carList);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        return spinner;
    }

    // Hierarchy Method of Spinners
    private void setUpAllSpinners() {

        // MAKE SPINNER ---------
        final Spinner spnMake = setUpSpinner(R.id.spn_make, makeDisplayList);
        if(!(selectMake == null)){
            spnMake.setSelection(makeDisplayList.indexOf(selectMake));
        }
        spnMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectMake = (String) spnMake.getSelectedItem();
                loadModelDisplayList();
                Log.i(TAG, selectMake);

                // MODEL SPINNER -----------
                final Spinner spnModel = setUpSpinner(R.id.spn_model, modelDisplayList);
                if(!(selectModel == null)){
                    spnModel.setSelection(modelDisplayList.indexOf(selectModel));
                }
                spnModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectModel = (String) spnModel.getSelectedItem();
                        Log.i(TAG, selectModel);


                        // YEAR SPINNER -----------------
                        // Pull Car List with specified make and model
                        final List<Car> specList = carCollection.search(selectMake, selectModel).toList();
                        Spinner spnYear = setUpCustomSpinner(R.id.spn_year, specList);
                        if(!(selectYear == null)){
                            int index = carCollection.search(selectMake, selectModel).getIndexOf(userSelectedCar);
                            spnYear.setSelection(index);
                        }
                        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                userSelectedCar = specList.get(i);
                                selectYear = Integer.toString(userSelectedCar.getYear());
                                Log.i(TAG, selectYear);
                                /// LOAD SOMETHING
                                updateCarInfo();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void updateCarInfo() {
        setUpTextView(R.id.tv_cityMPG, Double.toString(userSelectedCar.getCityMPG()));
        setUpTextView(R.id.tv_highwayMPG, Double.toString(userSelectedCar.getHighwayMPG()));
        setUpTextView(R.id.tv_transmission, userSelectedCar.getTransDescription());
        setUpTextView(R.id.tv_engine, userSelectedCar.getEngineDescription());

    }

    private void setUpTextView(int tvID, String text) {
        TextView tv = (TextView) findViewById(tvID);
        tv.setText(text);
    }

    private void loadCarList() {
        carCollection = new CarCollection(CarManager.readCarData(this, R.raw.vehicle_trimmed));
    }

    private void loadModelDisplayList() {
        modelDisplayList.clear();
        for (String model :
                carCollection.getCarMaker(selectMake).modelToStringList()) {
            if (!modelDisplayList.contains(model)) {
                modelDisplayList.add(model);
            }
        }
    }

    // Filters Through CarCollection extract unique Makers to String list
    private void loadMakeDisplayList() {
        makeDisplayList.clear();
        for (String make :
                carCollection.makeToStringList()) {
            if (!makeDisplayList.contains(make)) {
                makeDisplayList.add(make);
            }
        }
        Log.i(TAG, "loadMakeDisplayList: " + makeDisplayList);
    }


}
