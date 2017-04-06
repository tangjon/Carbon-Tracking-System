package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
        setupSettingsBtn();

        hideSystemUI();
    }

    private void hideSystemUI() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_menu);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSystemUI();
            }
        });

        View mDecorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.

        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        mDecorView.setSystemUiVisibility(uiOptions);
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

    private void setupSettingsBtn(){
        Button btn = (Button) findViewById(R.id.btnMainActivityToSettings);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SettingsActivity.getIntent(Temp_Main_Acticity.this);
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
