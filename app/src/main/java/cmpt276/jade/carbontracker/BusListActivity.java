package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import cmpt276.jade.carbontracker.model.Bus;
import cmpt276.jade.carbontracker.model.BusCollection;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;


/**
 * Created by Sean
 * Setup a list of buses user can choose from or can edit or add buses to the list
 */

public class BusListActivity extends AppCompatActivity {

    public static BusCollection busList = new BusCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        setupAddBtn();
        setupListview();
    }


    private void setupAddBtn(){
        Button btn = (Button) findViewById(R.id.btnBusAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Emission.getInstance().getJourneyBuffer().getTransType().setBus(busList.getBus(position));
                Intent intent = Route_List_Activity.IntentForRouteList(BusListActivity.this,3);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO
                //Get group to explain EditDialog too tired to figure it out how to get it working

                return false;
            }
        });
    }


    // Inspired by Raz
    private void setupDeleteAlert( final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Bus thisBus = busList.getBus(index);
        builder.setMessage(getString(R.string.journey_list_confirm_delete_message, thisBus.getNickName()));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.label_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                busList.deleteBus(index);
                populateList();
            }
        });

        builder.setNegativeButton(getString(R.string.label_cancel), null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void populateList(){
        //TODO
        //Make Adaptor
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, BusListActivity.class);
    }
}
