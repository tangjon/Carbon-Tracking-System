package cmpt276.jade.carbontracker.sample;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cmpt276.jade.carbontracker.CarListActivity;
import cmpt276.jade.carbontracker.Route_List_Activity;
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
import cmpt276.jade.carbontracker.model.Transportation;
import cmpt276.jade.carbontracker.enums.BillType;

/**
 * Generate Dummy Data to the entire application
 * FOR TESTING PURPOSES ONLY
 */

public class GenerateDummyData {
    private static String TAG = "LOADDUMMYCLASS";
    private static JourneyCollection jDummy;
    private static CarCollection cData;
    private static String instance = null;
    public static int MAX = 1000;

    private GenerateDummyData() {

    }

    // Generates a Route and Car into the recent lists
    // Does not generate a journey..
    public static void generateAll() {

        if (instance == null) {

            Log.i(TAG, "load: ");
            CarListActivity.recentCarList.add(generateCar());
            CarListActivity.recentCarList.add(generateCar());
            //Route_List_Activity.routes.addRoute(generateRoute());
            //Route_List_Activity.routes.addRoute(generateRoute());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());
            Emission.getInstance().getJourneyCollection().addJourney(generateJourney());


            instance = "LOADED!";
        }
    }

    public static void generateRecentLists() {
        CarListActivity.recentCarList.add(generateCar());
        CarListActivity.recentCarList.add(generateCar());
        CarListActivity.recentCarList.add(generateCar());

        Route_List_Activity.CarRoutesList.addRoute(generateRoute());
        Route_List_Activity.CarRoutesList.addRoute(generateRoute());
        Route_List_Activity.BikeNWalkRoutesList.addRoute(generateRoute());
        Route_List_Activity.BikeNWalkRoutesList.addRoute(generateRoute());
        Route_List_Activity.SkytrainRoutesList.addRoute(generateRoute());
        Route_List_Activity.SkytrainRoutesList.addRoute(generateRoute());
        Route_List_Activity.BusRoutesList.addRoute(generateRoute());
        Route_List_Activity.BusRoutesList.addRoute(generateRoute());
    }


    public static Car generateCar() {
        Random rn = new Random();
        int index = rn.nextInt(Emission.getInstance().getCarCollection().getSize()) - 1;
        Car car = Emission.getInstance().getCarCollection().getCar(index);
        car.setNickname("Car" + index % 20);
        return car;
    }

    public static Route generateRoute() {
        Random rn = new Random();
        int index = rn.nextInt(MAX);
        return new Route("Route" + index, rn.nextInt(MAX), rn.nextInt(MAX));
    }

    public static Bus generateBus() {
        Random rn = new Random();
        return new Bus("Bus " + rn.nextInt(MAX), "Route " + rn.nextInt(MAX));
    }

    public static Skytrain generateSkytrain() {
        Random rn = new Random();
        return new Skytrain("Skytrain" + rn.nextInt(MAX), "Station" + rn.nextInt(MAX), "Line" + rn.nextInt(MAX));
    }

    public static Journey generateJourney() {
        Random rn = new Random();
        int index = rn.nextInt(500);
        Car car = generateCar();
        Transportation trans = new Transportation();
        trans.setCar(car);
        trans.setTransMode(Transport.CAR);
        Route route = generateRoute();
        Journey journey = new Journey("Journey " + index, trans, route);
        journey.setDate("" + (rn.nextInt(26) + 1) + "/" + (rn.nextInt(12) + 1) + "/2016");
        return journey;
    }

    public static Bill generateBill() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        BillType type = null;

        Random rn = new Random();
        int ran = rn.nextInt(2);
        for (BillType t : BillType.values()) {
            if (ran == t.ordinal()) {
                type = t;
            }
        }


        return new Bill(type, date, date, rn.nextDouble() + rn.nextInt(10000));
    }

    public static Journey generateComplexJourney() {
        Transport mode = null;
        Random rn = new Random();

        // 6 Enums
        int index = rn.nextInt(6);
        for (Transport trans : Transport.values()) {
            if (trans.ordinal() == index) {
                mode = trans;
                break;
            }
        }

        // Create a Journey
        Transportation transportation = new Transportation();
        // Set the Mode
        transportation.setTransMode(mode);
        Route route = generateRoute();
        switch (mode) {
            case CAR:
                transportation.setCar(generateCar());
                break;
            case BIKE:
                // do nothing
                break;
            case WALK:
                // do nothing
                break;
            case BUS:
                transportation.setBus(generateBus());
                break;
            case SKYTRAIN:
                transportation.setSkytrain(generateSkytrain());
                break;
        }

        // Generate Date
        Journey journey = new Journey("Journey" + rn.nextInt(MAX), transportation, route);
        journey.setDate("" + (rn.nextInt(26) + 1) + "/" + (rn.nextInt(1) + 1) + "/2017");
        return journey;

    }

    static {
        Log.i(TAG, "static initializer: ");
        cData = Emission.getInstance().getCarCollection();
    }


}
