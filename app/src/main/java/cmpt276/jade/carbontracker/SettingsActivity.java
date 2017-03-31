package cmpt276.jade.carbontracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmpt276.jade.carbontracker.R;
import cmpt276.jade.carbontracker.enums.Language;
import cmpt276.jade.carbontracker.enums.MeasurementUnit;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.Settings;
import cmpt276.jade.carbontracker.model.Transportation;

public class SettingsActivity extends AppCompatActivity {

        private double testEmission = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        testSettings();
        setupPage();
        setupButtons();

    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    private void setupPage() {
        Button sillyMode = (Button) findViewById(R.id.btnSettingSillyMode);
        Button languageMode = (Button) findViewById(R.id.btnSettingsLanguage);
        Button toAbout = (Button) findViewById(R.id.btnSettingsToAbout);
        if(Emission.getInstance().getSettings().getSillyMode() == MeasurementUnit.TREES){
            sillyMode.setText("Disable Silly Mode");
            TextView test = (TextView) findViewById(R.id.textViewMoreLikeTestViewAmirite);
            test.setText("" + Emission.getInstance().getSettings().calcTreeAbsorbtion(testEmission) + " Tree Hours");
        }

        else {
            sillyMode.setText("Enable Silly Mode");
            TextView test = (TextView) findViewById(R.id.textViewMoreLikeTestViewAmirite);
            test.setText("" + testEmission + " Kg");
        }

        //This is probably just temporary until we get everything working
        if(Emission.getInstance().getSettings().getLanguageMode() == Language.ENGLISH)
           languageMode.setText("English");
        else if(Emission.getInstance().getSettings().getLanguageMode() == Language.SPANISH)
            languageMode.setText("Español");
        else if(Emission.getInstance().getSettings().getLanguageMode() == Language.FRENCH)
            languageMode.setText("Français");

        toAbout.setText("About");
    }

    private void setupButtons(){
        Button sillyMode = (Button) findViewById(R.id.btnSettingSillyMode);
        Button languageMode = (Button) findViewById(R.id.btnSettingsLanguage);
        Button toAbout = (Button) findViewById(R.id.btnSettingsToAbout);
        //Set up Silly Mode to enable or disable
        sillyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Emission.getInstance().getSettings().getSillyMode() == MeasurementUnit.TREES) {
                    Emission.getInstance().getSettings().setSillyMode(MeasurementUnit.REGULAR);
                    setupPage();
                }
                else {
                    Emission.getInstance().getSettings().setSillyMode(MeasurementUnit.TREES);
                    setupPage();
                }

                //TODO create a method that will convert everything to Trees
            }
        });

        languageMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Making a dialogue with radio buttons to replace this
                if(Emission.getInstance().getSettings().getLanguageMode() == Language.ENGLISH){
                    Emission.getInstance().getSettings().setLanguageMode(Language.SPANISH);
                    setupPage();
                }
                else if(Emission.getInstance().getSettings().getLanguageMode() == Language.SPANISH){
                    Emission.getInstance().getSettings().setLanguageMode(Language.FRENCH);
                    setupPage();
                }
                else if(Emission.getInstance().getSettings().getLanguageMode() == Language.FRENCH)
                    Emission.getInstance().getSettings().setLanguageMode(Language.ENGLISH);
                    setupPage();
                }
        });

        toAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = About.getIntent(SettingsActivity.this);
             //   startActivity(intent);
            }
        });
    }


    //DELETE THIS it is for testing only
    public void testSettings(){
        Emission.getInstance().setSettings(new Settings());
    }
}
