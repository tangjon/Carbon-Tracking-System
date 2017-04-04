package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import cmpt276.jade.carbontracker.adapter.BusListAdapter;
import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.fragment.EditDialog;
import cmpt276.jade.carbontracker.model.Bus;
import cmpt276.jade.carbontracker.model.BusCollection;
import cmpt276.jade.carbontracker.model.Emission;


/**
 * Created by Sean
 * Setup a list of buses user can choose from or can edit or add buses to the list
 */

public class BusListActivity extends AppCompatActivity {

    public static BusCollection recentBusList = new BusCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.BustListActivityToolBarHint);

        setupAddBtn();
        setupListview();
        populateList();
    }


    private void setupAddBtn() {
        Button btn = (Button) findViewById(R.id.btnBusAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Emission.getInstance().getJourneyBuffer().getTransType().setBus(new Bus());
                Intent intent = BusInfoActivity.getIntent(BusListActivity.this);
                startActivity(intent);
            }
        });

    }


    private void setupListview() {


        ListView list = (ListView) findViewById(R.id.listviewBus);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Emission.getInstance().getJourneyBuffer().getTransType().setBus(recentBusList.getBus(position));
                Intent intent = Route_List_Activity.IntentForRouteList(BusListActivity.this, 3);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Bus bus = recentBusList.getBus(position);
                EditDialog editDialog = EditDialog.newInstance(bus.getNickName(), Transport.BUS);
                editDialog.setPosition(position);
                editDialog.setEditDialogListener(new EditDialog.EditDialogListener() {
                    @Override
                    public void onDeleteClicked(int pos) {
                        setupDeleteAlert(pos);
                        populateList();
                    }

                    @Override
                    public void onEditClicked(int pos) {
                        Intent intent = BusInfoActivity.getIntent(BusListActivity.this);
                        Emission.getInstance().getJourneyBuffer().getTransType().setBus(recentBusList.getBus(pos));
                        Emission.getInstance().getJourneyBuffer().getTransType().getBus().setPosition(pos);
                        Emission.getInstance().getJourneyBuffer().getTransType().getBus().setMode(1);
                        startActivity(intent);
                    }
                });
                editDialog.show(getSupportFragmentManager(), "EditDialog");
                return true;
            }
        });
    }

    private void dbRefreshBusList() {
        DBAdapter db = new DBAdapter(this);
        db.open();
        // Complete Refresh RecentCarList DB
//        BusCollection bC = db.getAllBus(DBAdapter.TAG_ID.RECENT);
//        for (Bus b: bC.getBusList()) {
//            db.deleteRow(DBAdapter.DB_TABLE.BUS,b.get)
//        }
        db.deleteAll(DBAdapter.DB_TABLE.BUS, DBAdapter.TAG_ID.RECENT);

        // RE-ADD REMAINING RECENTS
        for (Bus b : recentBusList.getBusList()) {
            db.insertRow(b, DBAdapter.TAG_ID.RECENT);
        }
        db.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        populateList();
    }

    // Inspired by Raz
    private void setupDeleteAlert(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Bus thisBus = recentBusList.getBus(index);
        builder.setMessage(getString(R.string.journey_list_confirm_delete_message, thisBus.getNickName()));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.label_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recentBusList.deleteBus(index);
                populateList();
            }
        });

        builder.setNegativeButton(getString(R.string.label_cancel), null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void populateList() {
        dbRefreshBusList();
        //TODO
        //Make Adaptor
        ListAdapter adapt = new BusListAdapter(this, recentBusList.getBusList());
        ListView list = (ListView) findViewById(R.id.listviewBus);
        list.setAdapter(adapt);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, BusListActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}
