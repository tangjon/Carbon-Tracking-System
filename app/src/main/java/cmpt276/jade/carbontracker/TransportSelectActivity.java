package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        setupSkytrainBtn();
    }

    //mode 1
    private void setupCarBtn() {
        TextView btnCar = (TextView) findViewById(R.id.Transport_select_car);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarListActivity.getIntentFromActivity(TransportSelectActivity.this);
                intent.putExtra("Journey", journey);
                startActivity(intent);
            }
        });
    }

    //mode 2
    private void setupWalkBtn() {
        TextView btnCar = (TextView) findViewById(R.id.Transport_select_bike);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Route_List_Activity.IntentForRouteList(TransportSelectActivity.this,2);
                startActivity(intent);
            }
        });
    }

    //mode 3
    private void setupBusBtn() {
        TextView btnCar = (TextView) findViewById(R.id.Transport_select_bus);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Route_List_Activity.IntentForRouteList(TransportSelectActivity.this,3);
                startActivity(intent);
            }
        });
    }

    //mode 4
    private void setupSkytrainBtn() {
        TextView btnCar = (TextView) findViewById(R.id.Transport_select_skytrain);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Route_List_Activity.IntentForRouteList(TransportSelectActivity.this,4);
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
