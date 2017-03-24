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

import java.util.List;

import cmpt276.jade.carbontracker.database.DBAdapter;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Bus;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.Skytrain;
import cmpt276.jade.carbontracker.sample.GenerateDummyData;
import cmpt276.jade.carbontracker.utils.CarManager;

/**
 * Display a animated welcome screen to user
 * Also loads csv at startup
 * loads information from DataBase to Emissions
 */

public class Welcome_Activity extends AppCompatActivity {


    private static final String TAG = "welcome_activity";
    DBAdapter myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome_);

        // Load Required Files to Emissions
        loadRequiredApplicationResources();

        setupLayoutClick();
        playAnimations();
        setupTap();
    }

    private void playAnimations() {
        setupMoving(R.id.welcome_car1, 500, 2800); //#1 car
        setupMoving(R.id.welcome_car3, -700, 2300);//#2 car
        setupMoving(R.id.welcome_person1, -500, 3500); //#1 person
        setupMoving(R.id.welcome_person2, 500, 3500);  //#2 person
        setupMoving(R.id.welcome_person3, 100, 3000);  //#3 person
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

        myDb = new DBAdapter(this);
        myDb.open();
        // Load Vehicles.csv
        Emission.getInstance().setCarCollection(new CarCollection(CarManager.readCarData(this, R.raw.vehicle_trimmed)));
        // Load Read from DataBase
        loadInformationFromDataBase();

        // TODO REMOVE THIS
        // generateDummyJourneys();
    }

    private void loadInformationFromDataBase() {
        myDb = new DBAdapter(this);
        myDb.open();
        // Load Saved JourneyList
        JourneyCollection dbJC = myDb.getAllJourney();
        Emission.getInstance().setJourneyCollection(dbJC);

        // Load Saved Bills
        List<Bill> dbBills = myDb.getAllBills();
        if (!dbBills.isEmpty()) {
            for (Bill b : dbBills) {
                Log.i(TAG, "loadInformationFromDataBase: " + b.toString());
                switch (b.getBillType()) {
                    case GAS:
                        Emission.getInstance().getUtilities().getListBillGas().add(b);
                        break;
                    case ELECTRIC:
                        Emission.getInstance().getUtilities().getListBillElec().add(b);
                        break;
                }
            }
        }

        // Load Recent Car Lists
        for (Car c : myDb.getAllCars(DBAdapter.TAG_ID.RECENT).toList()) {
            CarListActivity.recentCarList.add(c);
            Log.i(TAG, "DBAdapterLoadRecetn:(CAR)" + c.toString());
        }

        // Load Recent Bus Lists
        for (Bus b : myDb.getAllBus(DBAdapter.TAG_ID.RECENT).getBusList()) {
            BusListActivity.recentBusList.addBus(b);
            Log.i(TAG, "DBAdapterLoadRecetn:(BUS)" + b.toString());
        }

        // Load Recent Skytrains
        for (Skytrain s : myDb.getAllSkytrain(DBAdapter.TAG_ID.RECENT).getTrainList()) {
            SkytrainListActivity.recentSkyTrainList.addTrain(s);
            Log.i(TAG, "DBAdapterLoadRecetn:(SKYTRAIN)" + s.toString());
        }

    }

    private void generateDummyJourneys() {
        if (Emission.getInstance().getJourneyCollection().getJourneyList().isEmpty()) {
            // Load some journeys
            Emission.getInstance().getJourneyCollection().addJourney(GenerateDummyData.generateComplexJourney());
            Emission.getInstance().getJourneyCollection().addJourney(GenerateDummyData.generateComplexJourney());
            Emission.getInstance().getJourneyCollection().addJourney(GenerateDummyData.generateComplexJourney());
            Emission.getInstance().getJourneyCollection().addJourney(GenerateDummyData.generateComplexJourney());
            Emission.getInstance().getJourneyCollection().addJourney(GenerateDummyData.generateComplexJourney());
            Emission.getInstance().getJourneyCollection().addJourney(GenerateDummyData.generateComplexJourney());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDb.close();
    }

    // IGNORE
    private void testSaveComplexJourney() {
        myDb = new DBAdapter(this);
        myDb.open();
        // Generate Journey
        Journey j = GenerateDummyData.generateComplexJourney();
        Log.i(TAG, "DBReadbefore: " + j.toString());
        long id = myDb.insertRow(j);
        j = myDb.getJourney(id);
        Log.i(TAG, "DBReadafter: " + j.toString());
    }

    // IGNORE
    private void testDataBaseSaveAndLoad() {
        DBAdapter db = new DBAdapter(this);
        db.open();
        // TEST CAR
        Car car = GenerateDummyData.generateCar();
        long carID = db.insertRow(car, DBAdapter.TAG_ID.MAIN);
        Car recCar = db.getCar(carID);
        Log.i(TAG, "testDataBaseSaveAndLoad: " + recCar.toString());
        // TEST ROUTE
        Route route = GenerateDummyData.generateRoute();
        long rRow = db.insertRow(route);
        Route recRoute = db.getRoute(rRow);
        Log.i(TAG, "testDataBaseSaveAndLoad: " + recRoute.toString());

        // Test Bus
        Bus bus = GenerateDummyData.generateBus();
        long busRow = db.insertRow(bus, DBAdapter.TAG_ID.MAIN);
        bus = db.getBus(busRow);
        Log.i(TAG, "testDataBaseSaveAndLoad: " + bus.toString());

        // Test Journey
        Journey journey = GenerateDummyData.generateJourney();
        journey.getTransType().setTransMode(Transport.CAR);
        long jRow = db.insertRow(journey);
        Journey recJ = db.getJourney(jRow);
        Log.i(TAG, "testDataBaseSaveAndLoad: " + recJ.toString());
        Log.i(TAG, "testDataBaseSaveAndLoad: " + recJ.getTransType().getCar().toString());

        // Test Skytrain
        Skytrain train = GenerateDummyData.generateSkytrain();
        long trainRow = db.insertRow(train, DBAdapter.TAG_ID.MAIN);
        train = db.getSkytrain(trainRow);
        Log.i(TAG, "testDataBaseSaveAndLoad: " + train.toString());

    }


}
