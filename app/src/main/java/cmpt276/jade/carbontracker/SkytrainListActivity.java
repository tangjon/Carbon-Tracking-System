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
import android.widget.ListAdapter;
import android.widget.ListView;

import cmpt276.jade.carbontracker.adapter.BusListAdapter;
import cmpt276.jade.carbontracker.adapter.SkytrainListAdaptor;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.fragment.EditDialog;
import cmpt276.jade.carbontracker.model.Bus;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Skytrain;
import cmpt276.jade.carbontracker.model.SkytrainCollection;

public class SkytrainListActivity extends AppCompatActivity {

    public static SkytrainCollection trainList = new SkytrainCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skytrain_list);

        setupAddBtn();
        setupListview();
        populateList();
    }

    private void setupAddBtn(){
        Button btn = (Button) findViewById(R.id.btnSkytrainListAdd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = BusInfoActivity.getIntent(SkytrainListActivity.this);
                startActivity(intent);
            }
        });

    }



    private void setupListview() {

        ListView list = (ListView) findViewById(R.id.listViewSkytrainList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Emission.getInstance().getJourneyBuffer().getTransType().setSkytrain(trainList.getTrain(position));
                Intent intent = Route_List_Activity.IntentForRouteList(SkytrainListActivity.this,3);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Skytrain train = trainList.getTrain(position);
                EditDialog editDialog = EditDialog.newInstance(train.getNickName(), Transport.SKYTRAIN);
                editDialog.setPosition(position);
                editDialog.setEditDialogListener(new EditDialog.EditDialogListener() {
                    @Override
                    public void onDeleteClicked(int pos) {
                        setupDeleteAlert(pos);
                        populateList();
                    }

                    @Override
                    public void onEditClicked(int pos) {
                        Intent intent = SkytrainInfoActivity.getIntent(SkytrainListActivity.this);
                        Emission.getInstance().getJourneyBuffer().getTransType().setSkytrain(trainList.getTrain(pos));
                        Emission.getInstance().getJourneyBuffer().getTransType().getSkytrain().setPosition(pos);
                        Emission.getInstance().getJourneyBuffer().getTransType().getSkytrain().setMode(1);
                        startActivity(intent);
                    }
                });
                editDialog.show(getSupportFragmentManager(),"EditDialog");
                return false;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        populateList();
    }


    // Inspired by Raz
    private void setupDeleteAlert( final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Skytrain thisTrain = trainList.getTrain(index);
        builder.setMessage(getString(R.string.journey_list_confirm_delete_message, thisTrain.getNickName()));
        builder.setCancelable(true);

        builder.setPositiveButton(getString(R.string.label_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                trainList.deleteTrain(index);
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
        ListAdapter adapt=new SkytrainListAdaptor(this,trainList.getSkytrainDetails());
        ListView list = (ListView) findViewById(R.id.listViewSkytrainList);
        list.setAdapter(adapt);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SkytrainListActivity.class);
    }

}
