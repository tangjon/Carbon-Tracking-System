package cmpt276.jade.carbontracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.sample.LoadDummyData;
import cmpt276.jade.carbontracker.utils.CarManager;

/**
 * Display a animated welcome screen to user
 * Also loads csv at startup
 */

public class Welcome_Activity extends AppCompatActivity {


    private static final String TAG = "welcome_activity" ;
    DBAdapter myDb;


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

    //Sean - makes screen clickable
    private void setupLayoutClick() {
        RelativeLayout click = (RelativeLayout) findViewById(R.id.activity_welcome_);
        //btn.setBackgroundResource(R.drawable.xxx);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Temp_Main_Acticity.getMainIntent(Welcome_Activity.this);
                startActivity(intent);
                Welcome_Activity.this.finish();
            }
        });


    }
    private void setupMoving(int id, int distance, int speed) {
        ImageView imageBTN = (ImageView) findViewById(id);
        imageBTN.animate().translationX(imageBTN.getTranslationX() + distance).setDuration(speed);
    }


    private void loadRequiredApplicationResources() {
        // Read vehicles.csv and population emissions CarCollection
        Log.i(TAG, "loadRequiredApplicationResources: " + "vehicles.csv loaded!");
        Emission.getInstance().setCarCollection(new CarCollection(CarManager.readCarData(this, R.raw.vehicle_trimmed)));

        // Todo Make Sure this is uncommented
        // Uncomment this to load dummy data
//         LoadDummyData.load();


        DBAdapter db = new DBAdapter(this);
        db.open();
        // TEST CAR
        Car car = LoadDummyData.generateCar();
        long carID = db.insertRow(car);
        Car recCar = db.getCar(carID);
        Log.i(TAG, "loadRequiredApplicationResources: " + recCar.toString());
//        displayRecordSetForCar(db.getAllRows(DBAdapter.DB_TABLE.CAR));

        // TEST ROUTE
        Route route = LoadDummyData.generateRoute();
        long carRow = db.insertRow(route);
        Route recRoute = db.getRoute(carRow);
        Log.i(TAG, "loadRequiredApplicationResources: " + recRoute.toString());
//        displayRecordSetForRoute(db.getAllRows(DBAdapter.DB_TABLE.ROUTE));

        Journey journey = LoadDummyData.generateJourney();
        journey.getTransType().setTransMode(Transport.CAR);
        long jRow = db.insertRow(journey);
        Journey recJ = db.getJourney(jRow);
        Log.i(TAG, "loadRequiredApplicationResources: " + recJ.toString());
        Log.i(TAG, "loadRequiredApplicationResources: " + recJ.getTransType().getCar().toString());

    }

    // KEEP THIS
    private void displayRecordSetForRoute(Cursor cursor) {
        String message = "";
        // populate the message from the cursor
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:

                double CityDistance = cursor.getDouble(DBAdapter.COL_ROUTE_CITY_DISTANCE);
                double HighWayDistance = cursor.getDouble(DBAdapter.COL_ROUTE_HIGH_WAY_DISTANCE);
                double OtherDistance = cursor.getDouble(DBAdapter.COL_ROUTE_OTHER_DISTANCE);//for bike,walk,bus,skytrain
                int mode = cursor.getInt(DBAdapter.COL_ROUTE_MODE);//2 for bike and walk,3 for bus, 4 for skytrain
                String name = cursor.getString(DBAdapter.COL_ROUTE_NAME);

                // Append data to the message:
                message += "name=" + name
                        +", CityDistance=" + CityDistance
                        +", HighWayDistance=" + HighWayDistance
                        +", OtherDistance=" + OtherDistance
                        +", mode=" + mode
                        +"\n";
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

        Log.i(TAG, "displayRecordSetForRoute: " + message);
    }
    private void displayRecordSetForCar(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        Car car = new Car();

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                double carbonTailPipe = cursor.getDouble(DBAdapter.COL_CAR_CARBON_TAIL_PIPE);
                double engineDispLitres = cursor.getDouble(DBAdapter.COL_CAR_ENGINE_DISP_LITRES);
                int cityMPG = cursor.getInt(DBAdapter.COL_CAR_CITY_MPG);
                int fuelAnnualCost= cursor.getInt(DBAdapter.COL_CAR_FUEL_ANNUAL_COST);
                int highwayMPG = cursor.getInt(DBAdapter.COL_CAR_HIGHWAY_MPG);
                int year = cursor.getInt(DBAdapter.COL_CAR_YEAR);
                String engineDescription = cursor.getString(DBAdapter.COL_CAR_ENGINE_DESCRIPTION);
                String fuelType = cursor.getString(DBAdapter.COL_CAR_FUEL_TYPE);
                String make = cursor.getString(DBAdapter.COL_CAR_MAKE);
                String model = cursor.getString(DBAdapter.COL_CAR_MODEL);
                String nickName = cursor.getString(DBAdapter.COL_CAR_NICK_NAME);
                String transDescription= cursor.getString(DBAdapter.COL_CAR_TRANS_DESCRIPTION);

                // Append data to the message:
                message += "make=" + make
                        +", model=" + model
                        +", cityMPG=" + cityMPG
                        +", fuelType=" + fuelType
                        +"\n";
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

        Log.i(TAG, "displayRecordSetForCar: " + message);
    }
}
