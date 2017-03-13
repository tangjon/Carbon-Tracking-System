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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cmpt276.jade.carbontracker.adapter.CarDetailSpinnerAdapter;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.utils.Mode;

/**
 * CarInfoActivity allows the users to select there specified car from drop downs.
 */

public class CarInfoActivity extends AppCompatActivity {
    // TAG
    private String TAG = "carinfoactivity";

    // KEY FOR DETERMINING KEY_APP_MODE
    private static String KEY_APP_MODE = "mode";

    // Field to contain all car info from vehicle.csv loaded from Emissions
    private CarCollection carCollection = Emission.getInstance().getCarCollection();

    // Helper Fields --------------------------------------
    private String selectMake, selectModel, selectYear;
    // String of Manufactures for spinner adapter
    private List<String> makeDisplayList = new ArrayList<>();
    // String of Models for Specific Car for spinner adapter
    private List<String> modelDisplayList = new ArrayList<>();
    // Field to store the user selected car <----------- THIS IS OF INTEREST
    private Car userSelectedCar;

    private Mode APP_MODE;

    // Get Intent with Mode Attached
    public static Intent getIntentFromActivity(Context context, Mode mode) {
        Intent intent = new Intent(context, CarInfoActivity.class);
        intent.putExtra(KEY_APP_MODE, mode);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        // Set Toolbar Name
        getSupportActionBar().setTitle(getString(R.string.CarInfoActivityHint));
        // get mode from intent
        APP_MODE = (Mode) getIntent().getExtras().getSerializable(KEY_APP_MODE);
        if(APP_MODE == null){
            APP_MODE = Mode.ADD;
        }

        switch (APP_MODE) {
            case ADD:
                // Create a new car
                userSelectedCar = new Car();
                loadSpecMakeDisplayList();
                setUpAllSpinners();
                setUpAddBtn();
                setUpCancelBtn();
                break;
            case EDIT:
                // Fetch Select Car to Edit
                UUID thisKey = loadCurrentCar();
                loadSpecMakeDisplayList();
                setUpAllSpinners();
                setUpFinishEditBtn(thisKey);
                setUpDeleteBtn(thisKey);
                break;
//
        }
    }

    // REFACTOR
    // Load Previous Car Info and get the key
    private UUID loadCurrentCar() {
        String key = getIntent().getExtras().getString(CarListActivity.CAR_KEY);
        userSelectedCar = CarListActivity.recentCarList.getCarByKey(key);
        Log.i(TAG, "onEdit: " + userSelectedCar);
        UUID thisKey = userSelectedCar.getKEY();
        selectMake = userSelectedCar.getMake();
        selectModel = userSelectedCar.getModel();
        selectYear = Double.toString(userSelectedCar.getYear());
        // Load Existing nickname
        EditText et = (EditText) findViewById(R.id.et_nickname);
        et.setText(userSelectedCar.getNickname());
        return thisKey;
    }

    private void setUpAddBtn() {
        // Link and Rename Button
        Button btn = (Button) findViewById(R.id.btn_next);
        btn.setText(getString(R.string.label_add));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.et_nickname);
                String nickName = et.getText().toString().trim();
                if(et.getText().toString().trim().length() == 0){
                    et.setError(getString(R.string.car_info_warning));
                }else{
                    userSelectedCar.setNickName(nickName);
                    // Assign new key
                    userSelectedCar.setKEY(UUID.randomUUID());
                    Car newCar = userSelectedCar.copy();
                    CarListActivity.recentCarList.add(newCar);
                    Log.i(TAG, "onAdd: " + userSelectedCar.toString());
                    finish();
                }
            }
        });
    }

    public void setUpDeleteBtn(final UUID key) {
        Button btn = (Button) findViewById(R.id.btn_delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelectedCar.setKEY(key);
                Boolean bool = CarListActivity.recentCarList.remove(userSelectedCar);
                Log.i(TAG, "onDelete: " + bool);
                finish();
            }
        });
    }

    public void setUpCancelBtn() {
        Button btn = (Button) findViewById(R.id.btn_delete);
        btn.setText(getString(R.string.lable_cancel));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpFinishEditBtn(final UUID key) {
        Button btn = (Button) findViewById(R.id.btn_next);
        final EditText et = (EditText) findViewById(R.id.et_nickname);
        btn.setText(R.string.carinfo_confirm_edit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelectedCar.setNickName(et.getText().toString().trim());
                userSelectedCar.setKEY(key);
                boolean bool = CarListActivity.recentCarList.updateCarInfo(userSelectedCar);
                Log.i(TAG, "onEditedConfirm: " + bool + ":" + userSelectedCar.toString());
                finish();
            }
        });

    }

    private Spinner setUpStringSpinner(int spnID, List<String> stringList) {
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
        CarDetailSpinnerAdapter adapter = new CarDetailSpinnerAdapter(CarInfoActivity.this, carList);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        return spinner;
    }

    // Hierarchy Method of Spinners
    private void setUpAllSpinners() {

        // MAKE SPINNER ---------
        final Spinner spnMake = setUpStringSpinner(R.id.spn_make, makeDisplayList);
        if (APP_MODE == Mode.EDIT) {
            spnMake.setSelection(makeDisplayList.indexOf(selectMake));
        }
        // MAKE SPINNER LISTENER ---------
        spnMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectMake = (String) spnMake.getSelectedItem();
                loadModelDisplayList(); // Specify Model String List from selectedMake

                // MODEL SPINNER -----------
                final Spinner spnModel = setUpStringSpinner(R.id.spn_model, modelDisplayList);
                if (APP_MODE == Mode.EDIT) {
                    spnModel.setSelection(modelDisplayList.indexOf(selectModel));
                }
                // MODEL SPINNER LISTENER -----------
                spnModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectModel = (String) spnModel.getSelectedItem();

                        // YEAR SPINNER -----------------
                        // Pull Car List with specified make and model
                        final List<Car> specList = carCollection.search(selectMake, selectModel).toList();
                        Spinner spnYear = setUpCustomSpinner(R.id.spn_year, specList);
                        if (APP_MODE == Mode.EDIT) {
                            int index = carCollection.search(selectMake, selectModel).getIndexOf(userSelectedCar);
                            spnYear.setSelection(index);
                        }
                        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                                // Grab Car from specList associate with the index on detailed Spinner
                                userSelectedCar = specList.get(pos);
                                Log.i(TAG, "userSelectedCar: " + userSelectedCar);
                                updateCarInfoToScreen();
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

    private void updateCarInfoToScreen() {
        setUpTextView(R.id.tv_cityMPG, Double.toString(userSelectedCar.getCityMPG()));
        setUpTextView(R.id.tv_highwayMPG, Double.toString(userSelectedCar.getHighwayMPG()));
        setUpTextView(R.id.tv_transmission, userSelectedCar.getTransDescription());
        setUpTextView(R.id.tv_engine, userSelectedCar.getEngineDescription());

    }

    private void setUpTextView(int tvID, String text) {
        TextView tv = (TextView) findViewById(tvID);
        tv.setText(text);
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
    private void loadSpecMakeDisplayList() {
        makeDisplayList.clear();
        for (String make : carCollection.makeToStringList()) {
            if (!makeDisplayList.contains(make)) {
                makeDisplayList.add(make);
            }
        }
    }


}
