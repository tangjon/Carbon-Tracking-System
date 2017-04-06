package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import cmpt276.jade.carbontracker.adapter.CarListAdapter;
import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.fragment.EditDialog;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.enums.Mode;

/**
 * Display a list of recently added Cars to user
 */

public class CarListActivity extends AppCompatActivity {
    // Field for Recent Car List
    public static CarCollection recentCarList = new CarCollection();

    // Car List Activity Static Resource Fields
    public static String CAR_KEY = "CARKEY";

    // Tag
    private String TAG = "carListActivity";

    private DBAdapter myDB;

    public static Intent getIntentFromActivity(Context context) {
        Intent intent = new Intent(context, CarListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        getSupportActionBar().setTitle(getString(R.string.CarListActivityHint));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Do DataBase Stuff
        myDB = new DBAdapter(this);
        myDB.open();


        // Set Up Buttons
        setUpAddButton(R.id.btn_add_car);
        // Populate the list
        populateListView();

        hideSystemUI();
    }

    private void hideSystemUI() {
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_menu);
//        layout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideSystemUI();
//            }
//        });

        View mDecorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.

        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;

        mDecorView.setSystemUiVisibility(uiOptions);
    }

    private void populateListView() {
        // Complete Refresh
        dbRefreshRecentCarList();
        // Link widget
        ListView lstView = (ListView) findViewById(R.id.lv_carList);
        // Create an ArrayAdapter using the string array and a default spinner layout
        CarListAdapter adapter = new CarListAdapter(CarListActivity.this, recentCarList.toList());
        // Apply the adapter to the spinner
        lstView.setAdapter(adapter);

        // React to Long Click (EDIT MODE)
        lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                EditDialog editDialog = EditDialog.newInstance(recentCarList.getCar(position).getName(), Transport.CAR);
                editDialog.setPosition(position);
                editDialog.setEditDialogListener(new EditDialog.EditDialogListener() {
                    @Override
                    public void onDeleteClicked(int pos) {
                        setupDelete(position);
                    }

                    @Override
                    public void onEditClicked(int pos) {
                        Intent intent = CarInfoActivity.getIntentFromActivity(CarListActivity.this, Mode.EDIT);
                        intent.putExtra(CAR_KEY, recentCarList.getCar(position).getKEY().toString());
                        startActivity(intent);
                    }
                });
                editDialog.show(getSupportFragmentManager(), "EditDialog");
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
                Intent intent = Route_List_Activity.IntentForRouteList(CarListActivity.this, 1);
                startActivity(intent);
            }
        });
    }

    private void dbRefreshRecentCarList() {
        // Complete Refresh RecentCarList DB
        CarCollection c = myDB.getAllCars(DBAdapter.TAG_ID.RECENT);
        // Delete Everything form DB with "RECENT"
        for (Car car : c.toList()) {
            myDB.deleteRow(DBAdapter.DB_TABLE.CAR, car.getID());
        }
        // RE-ADD REMAINING RECENTS
        for (Car car : recentCarList.toList()) {
            myDB.insertRow(car, DBAdapter.TAG_ID.RECENT);
        }
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

    private void updateUI() {
        populateListView();
    }


    // Inspired by Raz
    private void setupDelete(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Car thisCar = recentCarList.getCar(index);
        builder.setMessage(getString(R.string.journey_list_confirm_delete_message, thisCar.getName()));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.label_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recentCarList.remove(index);
//                boolean bool = myDB.deleteRow(DBAdapter.DB_TABLE.CAR,thisCar.getID());
//                Toast.makeText(CarListActivity.this, "onDelete:" + bool, Toast.LENGTH_SHORT).show();
                updateUI();
            }
        });

        builder.setNegativeButton(getString(R.string.label_cancel), null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    // Refresh UI upon returning to activity

    @Override
    protected void onRestart() {
        super.onRestart();
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
