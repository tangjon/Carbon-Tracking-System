package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import cmpt276.jade.carbontracker.adapter.CarListAdapter;
import cmpt276.jade.carbontracker.fragment.EditDialog;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.utils.Mode;

/**
 * Display a list of recently added Cars to user
 */

public class CarListActivity extends AppCompatActivity implements EditDialog.EditDialogListener {
    // Field for Recent Car List
    public static CarCollection recentCarList = new CarCollection();

    // Car List Activity Static Resource Fields
    public static String CAR_KEY = "CARKEY";

    // Tag
    private String TAG = "carListActivity";


    public static Intent getIntentFromActivity(Context context) {
        Intent intent = new Intent(context, CarListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        getSupportActionBar().setTitle(getString(R.string.CarListActivityHint));

        // Set Up Buttons
        setUpAddButton(R.id.btn_add_car);
        // Populate the list
        populateListView();

    }

    private void populateListView() {
        // Link widget
        ListView lstView = (ListView) findViewById(R.id.lv_carList);
        // Create an ArrayAdapter using the string array and a default spinner layout
        CarListAdapter adapter = new CarListAdapter(CarListActivity.this, recentCarList.toList());
        // Apply the adapter to the spinner
        lstView.setAdapter(adapter);

        // React to Long Click (EDIT MODE)
        lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                EditDialog editDialog = new EditDialog();
                editDialog.show(getSupportFragmentManager(),"HELLO");
//                setUpEditInterface(position);
//                Car car = (Car) parent.getAdapter().getItem(position);
//                Intent intent = CarInfoActivity.getIntentFromActivity(CarListActivity.this, Mode.EDIT);
//                intent.putExtra(CAR_KEY, car.getKEY().toString());
//                startActivity(intent);
                return true;
            }
        });

        // React to Car Object Click, send to CarListActivity
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Sean- adding my journey object to your intent
                Car car = (Car) parent.getAdapter().getItem(position);
                Emission.getInstance().getJourneyBuffer().getTransType().setCar(car);
                Intent intent = Route_List_Activity.IntentForRouteList(CarListActivity.this,1);
                startActivity(intent);
            }
        });
    }

    private void setUpAddButton(int btnID) {
        Button button = (Button) findViewById(btnID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ACTIVITIES
                Intent intent = CarInfoActivity.getIntentFromActivity(CarListActivity.this, Mode.ADD);
                startActivity(intent);
            }
        });
    }

    private void updateUI(){
        populateListView();
    }

    // Refresh UI upon returning to activity

    @Override
    protected void onRestart() {
        super.onRestart();
        updateUI();
    }

    /*
    //Sean - Gets journey object passed by journey list
    public void getJourneyData() {
        Intent intent = getIntent();
            journey = (Journey)intent.getSerializableExtra("Journey");

    }
    */

    private void setUpEditInterface(final int index){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_fragment_edit, null);
        Button btn_delete = (Button) mView.findViewById(R.id.btn_delete);
        Button btn_edit = (Button) mView.findViewById(R.id.btn_edit);
        builder.setView(mView);
        final AlertDialog alert = builder.create();
        alert.show();
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupDelete(index);
                alert.dismiss();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarInfoActivity.getIntentFromActivity(CarListActivity.this, Mode.EDIT);
                intent.putExtra(CAR_KEY, recentCarList.getCar(index).getKEY().toString());
                startActivity(intent);
                alert.dismiss();
            }
        });
    }


    // Inspired by Raz
    private void setupDelete( final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Car thisCar = recentCarList.getCar(index);
        builder.setMessage(getString(R.string.journey_list_confirm_delete_message, thisCar.getName()));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.label_delete), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recentCarList.remove(index);
                updateUI();
            }
        });

        builder.setNegativeButton(getString(R.string.label_cancel), null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onEditDialogDelete() {

    }

    @Override
    public void onEditDialogEdit() {

    }
}
