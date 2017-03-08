package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cmpt276.jade.carbontracker.model.Journey;

public class TransportSelectActivity extends AppCompatActivity {
    private Journey journey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_select);

        setupButtons();
        getIntentData();
    }

    private void setupButtons() {
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
