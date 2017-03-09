package cmpt276.jade.carbontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.utils.CarManager;

/**
 * Display a animated welcome screen to user
 * Also loads csv at startup
 */

public class Welcome_Activity extends AppCompatActivity {


    private static final String TAG = "welcome_activity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome_);
        // Load Required Files to Emissions
        loadRequiredApplicationResources();

        setupLayoutClick();
        setupMoving(R.id.welcome_car1, 500, 2800); //#1 car
        setupMoving(R.id.welcome_car3, -700, 2300);//#2 car
        setupMoving(R.id.welcome_person1, -500, 3500); //#1 person
        setupMoving(R.id.welcome_person2, 500, 3500);  //#2 person
        setupMoving(R.id.welcome_person3, 100, 3000);  //#3 person
        setupTap();
       // See method for reason of removal
        // setupAppName();
    }
    //Sean - Sets up flashing text
    private void setupTap() {
        TextView flash = (TextView) findViewById(R.id.tapToContinue);
        AlphaAnimation anim = new AlphaAnimation(0.2f, 1.0f);
        anim.setDuration(600);
        anim.setStartOffset(0);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        flash.startAnimation(anim);
    }

    private void loadRequiredApplicationResources() {
        // Read vehicles.csv and population emissions CarCollection
        Log.i(TAG, "loadRequiredApplicationResources: " + "vehicles.csv loaded!");
        Emission.getInstance().setCarCollection(new CarCollection(CarManager.readCarData(this, R.raw.vehicle_trimmed)));

        // Todo Make Sure this is uncommented
        // Uncomment this to load dummy data
        // LoadDummyData.load();
    }
    //Sean - makes screen clickable
    private void setupLayoutClick() {
        RelativeLayout click = (RelativeLayout) findViewById(R.id.activity_welcome_);
        //btn.setBackgroundResource(R.drawable.xxx);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = JourneyListActivity.getJourneyListIntent(Welcome_Activity.this);
                startActivity(intent);
                Welcome_Activity.this.finish();
            }
        });


    }

    private void setupMoving(int id, int distance, int speed) {
        ImageView imageBTN = (ImageView) findViewById(id);
        imageBTN.animate().translationX(imageBTN.getTranslationX() + distance).setDuration(speed);
    }

}
