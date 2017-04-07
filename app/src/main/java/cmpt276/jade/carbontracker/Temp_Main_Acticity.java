package cmpt276.jade.carbontracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import android.widget.Toast;
import cmpt276.jade.carbontracker.model.Emission;
import java.util.Calendar;

/**
 * Buttons lead you to graphs journey list or utilities temporary until we figure out something prettier
 */
public class Temp_Main_Acticity extends AppCompatActivity {

    public static Intent getMainIntent(Context context) {
        return new Intent(context, Temp_Main_Acticity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp__main__acticity);


        setupJourneyBtn();
        setupGraphBtn();
        setupUtilitiesBtn();
        setupSettingsBtn();

        startJourneyListAndUtilitiesList();
        setupNotifacation();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;

        mDecorView.setSystemUiVisibility(uiOptions);
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

    private void setupSettingsBtn() {
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

    private void startJourneyListAndUtilitiesList() {
        int mode = getIntent().getIntExtra("mode", 0);

        //Toast.makeText(getApplicationContext(), " "+mode, Toast.LENGTH_LONG).show();

        if (mode == 1) {
            Intent intent = new Intent();
            intent.setClass(this, JourneyListActivity.class);
            startActivity(intent);
        } else if (mode == 2) {
            Intent intent = new Intent();
            intent.setClass(this, Utilities_Activities.class);
            startActivity(intent);
        }
    }

    public void setupNotifacation() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 21);
        //calendar.set(Calendar.MINUTE, 00);

        Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //AlarmManager.INTERVAL_DAY, pendingIntent);

    }


}
