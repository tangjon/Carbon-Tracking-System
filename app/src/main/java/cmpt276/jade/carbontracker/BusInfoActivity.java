package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cmpt276.jade.carbontracker.model.Bus;
import cmpt276.jade.carbontracker.model.Emission;

public class BusInfoActivity extends AppCompatActivity {

    private Bus incomingBus;
    private Bus outgoingBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        getBusData();
        setupPage();
        setupNextBtn();
    }


    public static Intent getIntent(Context context) {
        return new Intent(context, BusInfoActivity.class);
    }

    private void setupPage(){
        if(Emission.getInstance().getJourneyBuffer().getTransType().getBus().getMode() == 1){
            EditText inputName = (EditText) findViewById(R.id.editTextBusNickname);
            inputName.setText(incomingBus.getNickName());
            EditText inputRouteNumber = (EditText) findViewById(R.id.editTextRouteNumber);
            inputRouteNumber.setText(incomingBus.getRouteNumber());
        }
    }

    private void setupNextBtn(){
        Button btn = (Button) findViewById(R.id.btnBusInfoNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If no list
                EditText inputName = (EditText) findViewById(R.id.editTextBusNickname);
                EditText inputRouteNumber = (EditText) findViewById(R.id.editTextRouteNumber);
                outgoingBus.setNickName(inputName.getText().toString().trim());
                outgoingBus.setRouteNumber(inputRouteNumber.getText().toString().trim());
                Emission.getInstance().getJourneyBuffer().getTransType().setBus(outgoingBus);
                Intent intent = Route_List_Activity.IntentForRouteList(BusInfoActivity.this, 3);
                startActivity(intent);
                finish();



                //Else if there is a list
                /*
                EditText inputName = (EditText) findViewById(R.id.editTextBusNickname);
                EditText inputRouteNumber = (EditText) findViewById(R.id.editTextRouteNumber);
                outgoingBus.setNickName(inputName.getText().toString().trim());
                outgoingBus.setRouteNumber(inputRouteNumber.getText().toString().trim());

                if (incomingBus.getMode() == 0) {
                    BusListActivity.busList.addBus(outgoingBus);
                }
                else if (incomingBus.getMode() == 1){
                    BusListActivity.busList.editBus(outgoingBus, incomingBus.getPosition());
                }
                Intent intent = BusListActivity.getIntent(BusInfoActivity.this);
                startActivity(intent);
                finish();
                */
            }
        });
    }

    public void getBusData() {
        this.incomingBus = Emission.getInstance().getJourneyBuffer().getTransType().getBus();
        this.outgoingBus = new Bus();
    }
}
