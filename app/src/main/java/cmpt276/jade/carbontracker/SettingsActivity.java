package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.enums.Language;
import cmpt276.jade.carbontracker.enums.MeasurementUnit;
import cmpt276.jade.carbontracker.model.Emission;

public class SettingsActivity extends AppCompatActivity {

    private double testEmission = 20;

    private DBAdapter myDB;

    public static Intent getIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(R.string.action_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Start communication with db
        myDB = new DBAdapter(this);
        myDB.open();

        testSettings();
        setupPage();
        setupButtons();

        hideSystemUI();
    }

    private void hideSystemUI() {
//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_menu);
//        layout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideSystemUI();
//            }
//        });

        View mDecorView = getWindow().getDecorView();
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.

        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;

        mDecorView.setSystemUiVisibility(uiOptions);
    }

    private void setupPage() {
        Button sillyMode = (Button) findViewById(R.id.btnSettingSillyMode);
        Button languageMode = (Button) findViewById(R.id.btnSettingsLanguage);
        Button toAbout = (Button) findViewById(R.id.btnSettingsToAbout);
        if (Emission.getInstance().getSettings().getSillyMode() == MeasurementUnit.TREES) {
            sillyMode.setText("Disable Silly Mode");
            TextView test = (TextView) findViewById(R.id.textViewMoreLikeTestViewAmirite);
            test.setText("" + Emission.getInstance().getSettings().calcTreeAbsorbtion(testEmission)
                    + " Tree Hours");
        } else {
            sillyMode.setText("Enable Silly Mode");
            TextView test = (TextView) findViewById(R.id.textViewMoreLikeTestViewAmirite);
            test.setText("" + testEmission + " Kg");
        }

        //This is probably just temporary until we get everything working
        if (Emission.getInstance().getSettings().getLanguageMode() == Language.ENGLISH) {
            languageMode.setText("English");
        } else if (Emission.getInstance().getSettings().getLanguageMode() == Language.SPANISH) {
            languageMode.setText("Español");
        } else if (Emission.getInstance().getSettings().getLanguageMode() == Language.FRENCH) {
            languageMode.setText("Français");
        }

        toAbout.setText("About");
    }

    private void setupButtons() {
        Button sillyMode = (Button) findViewById(R.id.btnSettingSillyMode);
        Button languageMode = (Button) findViewById(R.id.btnSettingsLanguage);
        Button toAbout = (Button) findViewById(R.id.btnSettingsToAbout);
        //Set up Silly Mode to enable or disable
        sillyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Emission.getInstance().getSettings().getSillyMode() == MeasurementUnit.TREES) {
                    Emission.getInstance().getSettings().setSillyMode(MeasurementUnit.REGULAR);
                    setupPage();
                } else {
                    Emission.getInstance().getSettings().setSillyMode(MeasurementUnit.TREES);
                    setupPage();
                }
                // Save settings changes to db;
                myDB.saveSettings(Emission.getInstance().getSettings());

                //TODO create a method that will convert everything to Trees
            }
        });

        languageMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Making a dialogue with radio buttons to replace this
                if (Emission.getInstance().getSettings().getLanguageMode() == Language.ENGLISH) {
                    Emission.getInstance().getSettings().setLanguageMode(Language.SPANISH);
                    setupPage();
                } else if (Emission.getInstance().getSettings().getLanguageMode() == Language.SPANISH) {
                    Emission.getInstance().getSettings().setLanguageMode(Language.FRENCH);
                    setupPage();
                } else if (Emission.getInstance().getSettings().getLanguageMode() == Language.FRENCH) {
                    Emission.getInstance().getSettings().setLanguageMode(Language.ENGLISH);
                }
                setupPage();

                // Save settings to db
                myDB.saveSettings(Emission.getInstance().getSettings());
            }
        });

        toAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AboutUsActivity.getIntent(SettingsActivity.this);
                startActivity(intent);
            }
        });
    }


    //DELETE THIS it is for testing only
    public void testSettings() {
//        Emission.getInstance().setSettings(new Settings());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
