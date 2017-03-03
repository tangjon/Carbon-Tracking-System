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

/**
 * Array positions to information
 * [0] - city08 - city MPG for fuelType1 (2), (11)
 * [1] - eng_dscr - engine descriptor; see http://www.fueleconomy.gov/feg/findacarhelp.shtml#engine
 * [2] - displ - engine displacement in liters
 * [3] - fuelCost08 - annual fuel cost for fuelType1 ($) (7)
 * [4] - fuelType1 - fuel type 1. For single fuel vehicles, this will be the only fuel. For dual fuel vehicles, this will be the conventional fuel.
 * [5] - highway08 - highway MPG for fuelType1 (2), (11)
 * [6] - model - model name (carline)
 * [7] - make - manufacturer (division)
 * [8] - co2TailpipeGpm- tailpipe CO2 in grams/mile for fuelType1 (5)
 */

public class CarManager {

    private static String TAG = "carmanager";

    private CarManager() {

    }

    public static ArrayList<Car> readCarData(Context context, int fileID) {
        // Initialize List for Dynamic Characteristics
        ArrayList<Car> carListData = new ArrayList<>();

        InputStream stream = context.getResources().openRawResource(fileID);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );
        String line = "";

        try {
            // Skip First Line due to headers
            line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                // Split by ','
                String[] tokens = line.split(",");
                // Parse Data
                //Array positions to information
                //[0] - city08 - city MPG for fuelType1 (2), (11)
                int cityMPG = Integer.parseInt(tokens[0]);
                //[1] - eng_dscr - engine descriptor; see http://www.fueleconomy.gov/feg/findacarhelp.shtml#engine
                String engineDescription = tokens[1];
                //[2] - displ - engine displacement in liters
                double engineDispLitres = Double.parseDouble(tokens[2]);
                //[3] - fuelCost08 - annual fuel cost for fuelType1 ($) (7)
                int fuelAnnualCost = Integer.parseInt(tokens[3]);
                //[4] - fuelType1 - fuel type 1. For single fuel vehicles, this will be the only fuel. For dual fuel vehicles, this will be the conventional fuel.
                String fuelType = tokens[4];
                //[5] - highway08 - highway MPG for fuelType1 (2), (11)
                int highwayMPG = Integer.parseInt(tokens[5]);
                //[6] - model - model name (carline)
                String model = tokens[6];
                //[7] - make - manufacturer (division)
                String make = tokens[7];
                //[8] - year
                int year = Integer.parseInt(tokens[8]);
                //[9] - co2TailpipeGpm- tailpipe CO2 in grams/mile for fuelType1 (5)
                double carbonTailPipe = Double.parseDouble(tokens[9]);

                // Generate Car with info above
                Car aCar = new Car(make, model, year, cityMPG, highwayMPG, engineDescription, engineDispLitres, fuelType, fuelAnnualCost, carbonTailPipe);
                carListData.add(aCar);

                // For Logging
                Log.i(TAG, "readCarData: " + aCar);
            }
        } catch (IOException e) {
            Log.wtf("CarManager", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }

        return carListData;

    }

}
