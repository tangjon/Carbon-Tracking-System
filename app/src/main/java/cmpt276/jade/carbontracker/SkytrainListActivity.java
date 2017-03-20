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

import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Skytrain;
import cmpt276.jade.carbontracker.model.SkytrainCollection;

public class SkytrainListActivity extends AppCompatActivity {

    public static SkytrainCollection trainList = new SkytrainCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skytrain_list);
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

                //TODO
                //Get group to explain EditDialog too tired to figure it out how to get it working

                return false;
            }
        });
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
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SkytrainListActivity.class);
    }

}
