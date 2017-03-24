package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Emission;

/*
 *  Allows user to select type of transportation (only car currently supported)
 */
public class TransportSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getString(R.string.TransportSelectActivityHint));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_select);

        setupCarBtn();
        setupBikeWalkBtn();
        setupBusBtn();
        setupSkytrainBtn();
    }

    //mode 1
    private void setupCarBtn() {
        TextView btnCar = (TextView) findViewById(R.id.Transport_select_car);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarListActivity.getIntentFromActivity(TransportSelectActivity.this);
                Emission.getInstance().getJourneyBuffer().getTransType().setTransMode(Transport.CAR);
                startActivity(intent);
            }
        });
    }

    //mode 2
    private void setupBikeWalkBtn() {
        TextView btnCar = (TextView) findViewById(R.id.Transport_select_bike);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Route_List_Activity.IntentForRouteList(TransportSelectActivity.this, 2);
                Emission.getInstance().getJourneyBuffer().getTransType().setTransMode(Transport.WALK);
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
                Intent intent = BusListActivity.getIntent(TransportSelectActivity.this);
                Emission.getInstance().getJourneyBuffer().getTransType().setTransMode(Transport.BUS);
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
                Intent intent = SkytrainListActivity.getIntent(TransportSelectActivity.this);
                Emission.getInstance().getJourneyBuffer().getTransType().setTransMode(Transport.SKYTRAIN);
                startActivity(intent);
            }
        });
    }


    public static Intent getTransportIntent(Context context) {
        return new Intent(context, TransportSelectActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


}
