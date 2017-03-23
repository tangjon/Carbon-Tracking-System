package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Buttons lead you to graphs journey list or utilities temporary until we figure out something prettier
 */
public class Temp_Main_Acticity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp__main__acticity);
        setupJourneyBtn();
        setupGraphBtn();
        setupUtilitiesBtn();
    }

    public static Intent getMainIntent(Context context) {
        return new Intent(context, Temp_Main_Acticity.class);
    }

    private void setupJourneyBtn() {
        Button btnCar = (Button) findViewById(R.id.Main_Journey_BTN);
        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = JourneyListActivity.getJourneyListIntent(Temp_Main_Acticity.this);
                startActivity(intent);
            }
        });
    }

    //TODO no route no graph
    private void setupGraphBtn() {
        Button button = (Button) findViewById(R.id.Main_Graph_BTN);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CarbonFootprintActivity.getIntent(Temp_Main_Acticity.this);
                startActivity(intent);
            }
        });
    }

    private void setupUtilitiesBtn() {
        Button button = (Button) findViewById(R.id.Main_Utilities_BTN);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Utilities_Activities.getUtilitiesIntent(Temp_Main_Acticity.this);
                startActivity(intent);
            }
        });
    }




}
