package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Utilities_Activities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilities__activities);

        setupButtons();
    }

    private void setupButtons() {
        Button btnNewElec = (Button) findViewById(R.id.btn_new_electric);
        Button btnNewGas = (Button) findViewById(R.id.btn_new_gas);

        btnNewElec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 12/03/17 add elec bill
            }
        });

        btnNewGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 12/03/17 add gas bill
            }
        });
    }

    public static Intent getUtilitiesIntent(Context context) {
        return new Intent(context, Utilities_Activities.class);
    }
}
