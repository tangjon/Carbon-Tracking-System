package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cmpt276.jade.carbontracker.model.Journey;

/*
 *  Allows user to select type of transportation (only car currently supported)
 */
public class TransportSelectActivity extends AppCompatActivity {
    private Journey journey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getString(R.string.TransportSelectActivityHint));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_select);

        setupCarBtn();
        setupWalkBtn();
        setupBusBtn();
        getIntentData();
    }

    private void setupCarBtn() {
        Button btnCar = (Button) findViewById(R.id.btn_transport_car);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarListActivity.getIntentFromActivity(TransportSelectActivity.this);
                intent.putExtra("Journey", journey);
                startActivity(intent);
            }
        });
    }


    private void setupWalkBtn() {
        Button btnCar = (Button) findViewById(R.id.transport_walk_btn);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Route_List_Activity.IntentForRouteList(TransportSelectActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupBusBtn() {
        Button btnCar = (Button) findViewById(R.id.transport_bus_btn);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Route_List_Activity.IntentForRouteList(TransportSelectActivity.this);
                startActivity(intent);
            }
        });
    }


    public static Intent getTransportIntent(Context context) {
        return new Intent(context, TransportSelectActivity.class);
    }

    public void getIntentData() {
        Intent intent = getIntent();
        Journey j;

        j = (Journey) intent.getSerializableExtra("Journey");
        if (j != null) journey = j;
    }
}
