package cmpt276.jade.carbontracker.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import cmpt276.jade.carbontracker.model.Car;

import static android.content.ContentValues.TAG;

/**
 * Created by tangj on 2/27/2017.
 */

public class CarManager {
    private CarManager() {

    }

    public static Car[] readCarData(Context context, int fileID){
        // Initialize List
        ArrayList<Car> carListData = new ArrayList<>();

        InputStream stream = context.getResources().openRawResource(fileID);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );
        String line = "";

        try {
            // Skip First Line due to headers
            line = reader.readLine();

            while (( line =reader.readLine()) != null){
                // Split by ','
                String[] tokens = line.split(",");
                // Parse Data
                String make = tokens[0];
                String model = tokens[1];
                int year = Integer.parseInt(tokens[2]);
                double uCity = Double.parseDouble(tokens[3]);
                double uHighway = Double.parseDouble(tokens[4]);
                double carbonTailPipe = Double.parseDouble(tokens[5]);
                Car aCar = new Car("", make, model, year, uCity,uHighway,carbonTailPipe);
                carListData.add(aCar);
                Log.i(TAG, "readCarData: " + aCar);
            }
        } catch (IOException e) {
            Log.wtf("CarManager", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }

        Car[] ret = carListData.toArray(new Car[carListData.size()]);
        return ret;

    }

}
