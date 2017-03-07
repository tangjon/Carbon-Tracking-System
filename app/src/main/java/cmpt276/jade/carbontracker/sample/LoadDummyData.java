package cmpt276.jade.carbontracker.sample;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Random;

import cmpt276.jade.carbontracker.CarListActivity;
import cmpt276.jade.carbontracker.Route_List_Activity;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Emission;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.utils.CarManager;

/**
 * Created by tangj on 3/7/2017.
 */

public class LoadDummyData {
    private static String TAG = "LOADDUMMYCLASS";
    private static JourneyCollection jDummy;
    private static CarCollection cData;

    private LoadDummyData() {

    }

    // Generates a Route and Car into the recent lists
    // Does not generate a journey..
    public static void load(){
        Log.i(TAG, "load: ");
        CarListActivity.recentCarList.add(generateCar());
        Route_List_Activity.routes.addRoute(generateRoute());
    }


    public static Car generateCar(){
        Random rn = new Random();
        int index = rn.nextInt(cData.toList().size()) - 1;
        Car car = cData.toList().get(index);
        car.setNickname("Car" + index);
        return car;
    }

    public static Route generateRoute(){
        Random rn = new Random();
        int index = rn.nextInt(100);
        return new Route("" + index, rn.nextInt(100),rn.nextInt(100));
    }

    static {
        Log.i(TAG, "static initializer: ");
        cData = Emission.getInstance().getCarCollection();
    }


}
