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
 * [1] - co2TailpipeGpm- tailpipe CO2 in grams/mile for fuelType1 (5)
 * [2] - displ - engine displacement in liters
 * [3] - eng_dscr - engine descriptor; see http://www.fueleconomy.gov/feg/findacarhelp.shtml#engine !! Field could be blank
 * [4] - fuelCost08 - annual fuel cost for fuelType1 ($) (7)
 * [5] - fuelType1 - fuel type 1. For single fuel vehicles, this will be the only fuel. For dual fuel vehicles, this will be the conventional fuel.
 * [6] - highway08 - highway MPG for fuelType1 (2), (11)
 * [7] - model - model name (carline)
 * [8] - make - manufacturer (division)
 * [9] - year
 * [10] - trans_dscr - transmission description !! Field could be blank
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

            int i = 0;
            while ((line = reader.readLine()) != null) {
                // Split by ','
                String[] tokens = line.split(",");
                // Parse Data
                //[0] - city08 - city MPG for fuelType1 (2), (11)
                int cityMPG = Integer.parseInt(tokens[0]);
                //[1] - co2TailpipeGpm- tailpipe CO2 in grams/mile for fuelType1 (5)
                double carbonTailPipe = Double.parseDouble(tokens[1]);
                //[2] - displ - engine displacement in liters
                double engineDispLitres = Double.parseDouble(tokens[2]);
                //[3] - eng_dscr - engine descriptor; see http://www.fueleconomy.gov/feg/findacarhelp.shtml#engine !! Field could be blank
                String engineDescription;
                if (tokens[3].contains("(")) {
                    engineDescription = tokens[3];
                } else {
                    engineDescription = null;
                }
                //[4] - fuelCost08 - annual fuel cost for fuelType1 ($) (7)
                int fuelAnnualCost = Integer.parseInt(tokens[4]);
                //[5] - fuelType1 - fuel type 1. For single fuel vehicles,
                // this will be the only fuel. For dual fuel vehicles, this will be the conventional fuel.
                String fuelType = tokens[4];
                //[6] - highway08 - highway MPG for fuelType1 (2), (11)
                int highwayMPG = Integer.parseInt(tokens[6]);
                //[7] - model - model name (carline)
                String model = tokens[7];
                //[8] - make - manufacturer (division)
                String make = tokens[8];
                //[9] - year
                int year = Integer.parseInt(tokens[9]);
                //[10] - trans_dscr - transmission description !! Field could be blank
                String transDescription;
                if (tokens.length > 10) {
                    transDescription = tokens[10];
                } else {
                    transDescription = null;
                }
                // Generate Car with info above
                Car aCar = new Car(make, model, year, cityMPG, highwayMPG, engineDescription, engineDispLitres, fuelType, fuelAnnualCost, carbonTailPipe, transDescription);
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
