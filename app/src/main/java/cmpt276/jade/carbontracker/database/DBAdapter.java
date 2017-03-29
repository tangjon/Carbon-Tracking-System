package cmpt276.jade.carbontracker.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import cmpt276.jade.carbontracker.model.Settings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cmpt276.jade.carbontracker.enums.Language;
import cmpt276.jade.carbontracker.enums.MeasurementUnit;
import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Bus;
import cmpt276.jade.carbontracker.model.BusCollection;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.RouteCollection;
import cmpt276.jade.carbontracker.model.Skytrain;
import cmpt276.jade.carbontracker.model.SkytrainCollection;
import cmpt276.jade.carbontracker.model.Transportation;
import cmpt276.jade.carbontracker.enums.BillType;

/*
* Class: DBAdapter
* Description: Data Base Adapter/Manager for saving to SQLlite database,
*              supports saving Journey, Route, Skytrain, Car, Bus, Walk,
*              Bike objects
* Bugs: none
* Todo: Save bills
* */

// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 31;

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";

    public static final String TABLE_ROUTE = "routes";

    // TRANSPORT TABLES
    public static final String TABLE_CAR = "cars";
    public static final String TABLE_JOURNEY = "journeys";
    public static final String TABLE_BUS = "buss";
    public static final String TABLE_SKYTRAIN = "skytrains";
    public static final String TABLE_WALK = "walks";
    public static final String TABLE_BIKE = "bikes";
    public static final String TABLE_TRANSIT = "transits";
    public static final String TABLE_OPTION = "options";

    // UTILITY TABLES
    public static final String TABLE_BILL = "bills";

    // General KEYS
    public static final String KEY_ROWID = "_id";
    public static final String KEY_IMG_ID = "_image_id";

    // General COL IDs
    public static final int COL_ROWID = 0;
    public static final int COL_IMG_ID = 1;

    // TODO: Setup Journey Fields Here
    public static final String KEY_JOURNEY_NAME = "journey_name";
    public static final String KEY_JOURNEY_TRANS_TYPE = "journey_trans_type";
    public static final String KEY_JOURNEY_DATE = "journey_date";
    public static final String KEY_JOURNEY_ROUTE_ID = "journey_route";
    public static final String KEY_TRANSPORT_OBJECT_ID = "journey_car";

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1= COL_IMG_ID...)
    public static final int COL_JOURNEY_NAME = 2;
    public static final int COL_JOURNEY_TRANS_TYPE = 3;
    public static final int COL_JOURNEY_DATE = 4;
    public static final int COL_JOURNEY_ROUTE_ID = 5;
    public static final int COL_TRANSPORT_OBJECT_ID = 6;
    // ALL KEYS
    public static final String[] ALL_JOURNEY_KEYS = new String[]{
            KEY_ROWID,
            KEY_IMG_ID,
            KEY_JOURNEY_NAME,
            KEY_JOURNEY_TRANS_TYPE,
            KEY_JOURNEY_DATE,
            KEY_JOURNEY_ROUTE_ID,
            KEY_TRANSPORT_OBJECT_ID};
    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_JOURNEY =
            "create table " + TABLE_JOURNEY
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // TODO: Place your fields here!

                    + KEY_IMG_ID + " integer, "
                    + KEY_JOURNEY_NAME + " text, "
                    + KEY_JOURNEY_TRANS_TYPE + " text, "
                    + KEY_JOURNEY_DATE + " text, "
                    + KEY_JOURNEY_ROUTE_ID + " integer, "
                    + KEY_TRANSPORT_OBJECT_ID + " integer"

                    // Rest  of creation:
                    + ");";

    // TODO: Setup Car Fields Here

    public static final String KEY_CAR_CARBON_TAIL_PIPE = "car_carbon_tail_pipe";
    public static final String KEY_CAR_CITY_MPG = "car_city_mpg";
    public static final String KEY_CAR_ENGINE_DESCRIPTION = "car_engine_descrip";
    public static final String KEY_CAR_ENGINE_DISP_LITRES = "car_engine_displacement";
    public static final String KEY_CAR_FUEL_ANNUAL_COST = "car_fuel_annual_cost";
    public static final String KEY_CAR_FUEL_TYPE = "car_fuel_type";
    public static final String KEY_CAR_HIGHWAY_MPG = "car_highway_mpg";
    public static final String KEY_CAR_MAKE = "car_make ";
    public static final String KEY_CAR_MODEL = "car_model";
    public static final String KEY_CAR_NICK_NAME = "car_nick_name";
    public static final String KEY_CAR_TRANS_DESCRIPTION = "car_descrip";
    public static final String KEY_CAR_YEAR = "car_year";
    public static final String KEY_CAR_TAG_ID = "car_tag";


    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1= COL_IMG_ID...)
    public static final int COL_CAR_CARBON_TAIL_PIPE = 2;
    public static final int COL_CAR_CITY_MPG = 3;
    public static final int COL_CAR_ENGINE_DESCRIPTION = 4;
    public static final int COL_CAR_ENGINE_DISP_LITRES = 5;
    public static final int COL_CAR_FUEL_ANNUAL_COST = 6;
    public static final int COL_CAR_FUEL_TYPE = 7;
    public static final int COL_CAR_HIGHWAY_MPG = 8;
    public static final int COL_CAR_MAKE = 9;
    public static final int COL_CAR_MODEL = 10;
    public static final int COL_CAR_NICK_NAME =  11;
    public static final int COL_CAR_TRANS_DESCRIPTION =  12;
    public static final int COL_CAR_YEAR =  13;
    public static final int COL_CAR_TAG_ID =  14;

    // ALL KEYS (Contains all KEYS in array of strings) (DETERMINES THE COLUMNS NUMBERS)
    public static final String[] ALL_CAR_KEYS = new String[]{
            KEY_ROWID,
            KEY_IMG_ID,
            KEY_CAR_CARBON_TAIL_PIPE,
            KEY_CAR_CITY_MPG,
            KEY_CAR_ENGINE_DESCRIPTION,
            KEY_CAR_ENGINE_DISP_LITRES,
            KEY_CAR_FUEL_ANNUAL_COST,
            KEY_CAR_FUEL_TYPE,
            KEY_CAR_HIGHWAY_MPG,
            KEY_CAR_MAKE,
            KEY_CAR_MODEL,
            KEY_CAR_NICK_NAME,
            KEY_CAR_TRANS_DESCRIPTION,
            KEY_CAR_YEAR,
            KEY_CAR_TAG_ID};

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_CAR =
            "create table " + TABLE_CAR
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // TODO: Place your fields here!
                    + KEY_IMG_ID + " integer, "
                    + KEY_CAR_CARBON_TAIL_PIPE + " real, "
                    + KEY_CAR_CITY_MPG + " real, "
                    + KEY_CAR_ENGINE_DESCRIPTION + " integer, "
                    + KEY_CAR_ENGINE_DISP_LITRES + " real, "
                    + KEY_CAR_FUEL_ANNUAL_COST + " integer, "
                    + KEY_CAR_FUEL_TYPE + " text, "
                    + KEY_CAR_HIGHWAY_MPG + " integer, "
                    + KEY_CAR_MAKE + " text, "
                    + KEY_CAR_MODEL + " text, "
                    + KEY_CAR_NICK_NAME + " text, "
                    + KEY_CAR_TRANS_DESCRIPTION + " text, "
                    + KEY_CAR_YEAR + " integer, "
                    + KEY_CAR_TAG_ID + " integer"

                    // Rest  of creation:
                    + ");";


    // TODO: Setup Route Fields Here

    public static final String KEY_ROUTE_CITY_DISTANCE = "city_distance";
    public static final String KEY_ROUTE_HIGH_WAY_DISTANCE = "high_way_distance";
    public static final String KEY_ROUTE_OTHER_DISTANCE = "other_distance";
    public static final String KEY_ROUTE_MODE = "mode";
    public static final String KEY_ROUTE_NAME = "name";
    public static final String KEY_ROUTE_TAG_ID = "route_tag_id";
    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1= COL_IMG_ID...)
    public static final int COL_ROUTE_CITY_DISTANCE = 2;
    public static final int COL_ROUTE_HIGH_WAY_DISTANCE = 3;
    public static final int COL_ROUTE_OTHER_DISTANCE = 4;
    public static final int COL_ROUTE_MODE = 5;
    public static final int COL_ROUTE_NAME = 6;
    public static final int COL_ROUTE_TAG_ID = 7;
    // ALL KEYS
    public static final String[] ALL_ROUTE_KEYS = new String[]{
            KEY_ROWID,
            KEY_IMG_ID,
            KEY_ROUTE_CITY_DISTANCE,
            KEY_ROUTE_HIGH_WAY_DISTANCE,
            KEY_ROUTE_OTHER_DISTANCE,
            KEY_ROUTE_MODE,
            KEY_ROUTE_NAME,
            KEY_ROUTE_TAG_ID
    };

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_ROUTE =
            "create table " + TABLE_ROUTE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

			/*
             * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_IMG_ID + " integer, "
                    + KEY_ROUTE_CITY_DISTANCE + " real,"
                    + KEY_ROUTE_HIGH_WAY_DISTANCE + " real,"
                    + KEY_ROUTE_OTHER_DISTANCE + " real,"
                    + KEY_ROUTE_MODE + " integer,"
                    + KEY_ROUTE_NAME + " text,"
                    + KEY_ROUTE_TAG_ID + " integer"
                    // Rest  of creation:
                    + ");";

    // TODO: Setup Bus Fields Here
    private static final String KEY_BUS_NICK_NAME = "bus_nick_name";
    private static final String KEY_BUS_ROUTE_NUMBER = "bus_route_number";
    private static final String KEY_BUS_TAG_ID = "bus_tag_id";

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1= COL_IMG_ID...)
    public static final int COL_BUS_NICK_NAME = 2;
    public static final int COL_BUS_ROUTE_NUMBER = 3;
    public static final int COL_BUS_TAG_ID = 4;

    // ALL KEYS
    public static final String[] ALL_BUS_KEYS = new String[]{
            KEY_ROWID,
            KEY_IMG_ID,
            KEY_BUS_NICK_NAME,
            KEY_BUS_ROUTE_NUMBER,
            KEY_BUS_TAG_ID
    };

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_BUS =
            "create table " + TABLE_BUS
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // TODO: Place your fields here!
                    + KEY_IMG_ID + " integer, "
                    + KEY_BUS_NICK_NAME + " text, "
                    + KEY_BUS_ROUTE_NUMBER + " text, "
                    + KEY_BUS_TAG_ID + " integer"

                    // Rest  of creation:
                    + ");";

    // TODO: Setup Skytrain Fields Here
    private static final String KEY_SKYTRAIN_NICK_NAME = "skytrain_nick_name";
    private static final String KEY_SKYTRAIN_BOARDING_STATION = "skytrain_boarding_station";
    private static final String KEY_SKYTRAIN_LINE = "key_skytrain_line";
    private static final String KEY_SKYTRAIN_TAG_ID = "key_skytrain_tag_id";
    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    private static final int COL_SKYTRAIN_NICK_NAME = 2;
    private static final int COL_SKYTRAIN_BOARDING_STATION = 3;
    private static final int COL_SKYTRAIN_LINE = 4;
    private static final int COL_SKYTRAIN_TAG_ID = 5;
    // ALL KEYS
    public static final String[] ALL_SKYTRAIN_KEYS = new String[]{
            KEY_ROWID,
            KEY_IMG_ID,
            KEY_SKYTRAIN_NICK_NAME,
            KEY_SKYTRAIN_BOARDING_STATION,
            KEY_SKYTRAIN_LINE,
            KEY_SKYTRAIN_TAG_ID
    };

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_SKYTRAIN =
            "create table " + TABLE_SKYTRAIN
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // TODO: Place your fields here!
                    + KEY_IMG_ID + " integer, "
                    + KEY_SKYTRAIN_NICK_NAME + " text, "
                    + KEY_SKYTRAIN_BOARDING_STATION + " text, "
                    + KEY_SKYTRAIN_LINE + " text, "
                    + KEY_SKYTRAIN_TAG_ID + " integer"

                    // Rest  of creation:
                    + ");";


    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    // ALL KEYS
    public static final String[] ALL_WALK_KEYS = new String[]{
    };
    // Create the Data Base (SQL)

    // TODO: Setup Bike Fields Here
    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    // ALL KEYS
    public static final String[] ALL_BIKE_KEYS = new String[]{
    };
    // Create the Data Base (SQL)
    // TODO: Setup Transit Fields Here
    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    // ALL KEYS
    public static final String[] ALL_TRANSIT_KEYS = new String[]{
    };
    // Create the Data Base (SQL)


    // TODO: Setup Utilities Fields Here

    // ----- TODO: Setup Bills
    public static final String KEY_BILL_TYPE = "bill_type";
    public static final String KEY_BILL_START_DATE = "bill_start_date";
    public static final String KEY_BILL_END_DATE = "bill_end_date";
    public static final String KEY_BILL_INPUT = "bill_input";

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    public static final int COL_BILL_TYPE = 1;
    public static final int COL_BILL_START_DATE = 2;
    public static final int COL_BILL_END_DATE = 3;
    public static final int COL_BILL_INPUT = 4;

    // ALL KEYS
    public static final String[] ALL_BILL_KEYS = new String[]{
            KEY_ROWID,
            KEY_BILL_TYPE,
            KEY_BILL_START_DATE,
            KEY_BILL_END_DATE,
            KEY_BILL_INPUT};

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_BILL =
            "create table " + TABLE_BILL
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    + KEY_BILL_TYPE + " int, "
                    + KEY_BILL_START_DATE + " int, "
                    + KEY_BILL_END_DATE + " int, "
                    + KEY_BILL_INPUT + " real"

                    // Rest  of creation:
                    + ");";

    /****************************************
     * SAVING OPTION MEMBERS
     * ****************************************/
    // SET UP KEYS
    public static final String KEY_OP_LANG = "lang";
    public static final String KEY_OP_MEASUREMENT_UNIT = "measurement_unit";

    // SETUP COLS
    public static final int COL_OP_LANG = 1;
    public static final int COL_OP_MEASUREMENT_UNIT = 2;

    // MASTER KEY
    public static final String[] ALL_OP_KEYS = new String[]{
            KEY_ROWID,
            KEY_OP_LANG,
            KEY_OP_MEASUREMENT_UNIT
    };

    // CREATE SQL TABLE
    private static final String CREATE_TABLE_OPTION =
            "create table " + TABLE_OPTION
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    + KEY_OP_LANG + " int, "
                    + KEY_OP_MEASUREMENT_UNIT + " int"

                    // Rest  of creation:
                    + ");";



    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    /****************************************
     * GENERAL FUNCTIONS
     * ****************************************/
    //Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(DB_TABLE table, long rowId) {
        Log.i(TAG, "[" + table + "]" + ":" + "deleteRow:" + rowId + "");
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(table.toString(), where, null) != 0;
    }

    public void deleteJourney(Journey j) {
        Log.i(TAG, "deleteJourney: ==================================");
        long rowId = j.getID();
        String where = KEY_ROWID + "=" + rowId;


        Cursor c = db.query(true, TABLE_JOURNEY, ALL_JOURNEY_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        if (c.getCount() > 0) {
            String TRANS_TYPE = c.getString(COL_JOURNEY_TRANS_TYPE);
            long routeID = c.getInt(COL_JOURNEY_ROUTE_ID);

            // Get and set Transport Type
            Transportation transportation = new Transportation();
            transportation.setTransMode(buffTrans(TRANS_TYPE));

            // Delete Transport Object
            switch (transportation.getTransMode()) {
                case CAR:
                    long CAR_ID = c.getInt(COL_TRANSPORT_OBJECT_ID);
                    deleteRow(DB_TABLE.CAR, CAR_ID);
                    break;
                case BIKE:
                    // Do nothing
                    break;
                case WALK:
                    // Do nothing
                    break;
                case BUS:
                    long busID = c.getInt(COL_TRANSPORT_OBJECT_ID);
                    deleteRow(DB_TABLE.BUS, busID);
                    break;
                case SKYTRAIN:
                    long trainID = c.getInt(COL_TRANSPORT_OBJECT_ID);
                    deleteRow(DB_TABLE.SKYTRAIN, trainID);
                    break;
            }
            // Delete Route
            deleteRow(DB_TABLE.ROUTE, routeID);

            // Delete Journey
            deleteRow(DB_TABLE.JOURNEY, rowId);
        }
    }

    public void deleteAll(DB_TABLE table) {
        Cursor c = getAllRows(table);
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(table, c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void deleteAll(DB_TABLE table, TAG_ID tag_id) {
        Cursor c = getAllRows(table);
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                long row = c.getInt(COL_ROWID);
                int tag = -1;
                switch (table) {
                    case CAR:
                        tag = c.getInt(COL_CAR_TAG_ID);
                        break;
                    case ROUTE:
                        tag = c.getInt(COL_ROUTE_TAG_ID);
                        break;
                    case BUS:
                        tag = c.getInt(COL_BUS_TAG_ID);
                        break;
                    case SKYTRAIN:
                        tag = c.getInt(COL_SKYTRAIN_TAG_ID);
                        break;
                }
                if (tag == tag_id.ordinal()) {
                    deleteRow(table, row);
                }
            } while (c.moveToNext());
        }
        c.close();
    }

    /****************************************
     * INSERT FUNCTIONS
     * *** TODO: STEP XXX create a insert function
     * ****************************************/

    public long insertRow(Car car, TAG_ID tag_id) {
        /*
         * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CAR_CARBON_TAIL_PIPE, car.getCarbonTailPipe());
        initialValues.put(KEY_CAR_CITY_MPG, car.getCityMPG());
        initialValues.put(KEY_CAR_ENGINE_DESCRIPTION, car.getEngineDescription());
        initialValues.put(KEY_CAR_ENGINE_DISP_LITRES, car.getEngineDispLitres());
        initialValues.put(KEY_CAR_FUEL_ANNUAL_COST, car.getFuelAnnualCost());
        initialValues.put(KEY_CAR_FUEL_TYPE, car.getFuelType());
        initialValues.put(KEY_CAR_HIGHWAY_MPG, car.getHighwayMPG());
        initialValues.put(KEY_CAR_MAKE, car.getMake());
        initialValues.put(KEY_CAR_MODEL, car.getModel());
        initialValues.put(KEY_CAR_NICK_NAME, car.getNickName());
        initialValues.put(KEY_CAR_TRANS_DESCRIPTION, car.getTransDescription());
        initialValues.put(KEY_CAR_YEAR, car.getYear());
        initialValues.put(KEY_CAR_TAG_ID, tag_id.ordinal());
        initialValues.put(KEY_IMG_ID, car.getImageId());

        // Insert it into the database.
        long buff = db.insert(TABLE_CAR, null, initialValues);
        Log.i(TAG, "[" + TABLE_CAR + "]" + ":" + "insert:" + buff + " " + tag_id + " " + car.toString());
        return buff;
    }

    public long insertRow(Bill bill) {
        /*
         * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BILL_TYPE, bill.getBillType().ordinal());
        initialValues.put(KEY_BILL_START_DATE, bill.getStartDate().getTime());
        initialValues.put(KEY_BILL_END_DATE, bill.getEndDate().getTime());
        initialValues.put(KEY_BILL_INPUT, bill.getInput());

        Log.i(TAG, "insertRowBill: " + bill.toString());
        // Insert it into the database.
        return db.insert(TABLE_BILL, null, initialValues);
    }

    public long insertRow(Route route) {
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROUTE_NAME, route.getName());
        initialValues.put(KEY_ROUTE_HIGH_WAY_DISTANCE, route.getHighWayDistance());
        initialValues.put(KEY_ROUTE_CITY_DISTANCE, route.getCityDistance());
        initialValues.put(KEY_ROUTE_OTHER_DISTANCE, route.getOtherDistance());
        initialValues.put(KEY_ROUTE_MODE, route.getMode());
        initialValues.put(KEY_IMG_ID, route.getImageId());
        // Insert it into the database.
        return db.insert(TABLE_ROUTE, null, initialValues);
    }

    public long insertRow(Bus bus, TAG_ID tag_id) {
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BUS_NICK_NAME, bus.getNickName());
        initialValues.put(KEY_BUS_ROUTE_NUMBER, bus.getRouteNumber());
        initialValues.put(KEY_BUS_TAG_ID, tag_id.ordinal());
        initialValues.put(KEY_IMG_ID, bus.getImageId());
        // Insert it into the database.

        long buff = db.insert(TABLE_BUS, null, initialValues);
        Log.i(TAG, "[" + TABLE_BUS + "]" + ":" + "insert:" + buff + " " + tag_id + " " + bus.toString());
        return buff;
    }

    public long insertRow(Skytrain skytrain, TAG_ID tag_id) {
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SKYTRAIN_NICK_NAME, skytrain.getNickName());
        initialValues.put(KEY_SKYTRAIN_BOARDING_STATION, skytrain.getBoardingStation());
        initialValues.put(KEY_SKYTRAIN_LINE, skytrain.getSkytrainLine());
        initialValues.put(KEY_SKYTRAIN_TAG_ID, tag_id.ordinal());
        initialValues.put(KEY_IMG_ID, skytrain.getImageId());
        // Insert it into the database.
        long buff = db.insert(TABLE_SKYTRAIN, null, initialValues);
        Log.i(TAG, "[" + TABLE_SKYTRAIN + "]" + ":" + "insert:" + buff + " " + tag_id + " " + skytrain.toString());
        return buff;
    }

    // *** STEP 2 ATTACH TO JOURNEY TABLE
    public long insertRow(Journey journey) {
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        ContentValues initialValues = new ContentValues();

        // Insert Journey Info
        initialValues.put(KEY_JOURNEY_NAME, journey.getName());
        initialValues.put(KEY_JOURNEY_TRANS_TYPE, journey.getTransType().getTransMode().toString());
        initialValues.put(KEY_JOURNEY_DATE, journey.getDate());
        initialValues.put(KEY_IMG_ID, journey.getImageId());

        // Insert Route
        long routeID = insertRow(journey.getRoute());
        initialValues.put(KEY_JOURNEY_ROUTE_ID, routeID);

        // Insert Transportation Object
        // (1) Insert Object
        // (2) Attach id to KEY_TRANSPORT_OBJECT_ID
        Transport mode = journey.getTransType().getTransMode();
        switch (mode) {
            case CAR:
                // Insert Car
                long carID = insertRow(journey.getTransType().getCar(), TAG_ID.MAIN);
                initialValues.put(KEY_TRANSPORT_OBJECT_ID, carID);
                break;
            case BIKE:
                // Do nothing
                break;
            case WALK:
                // Do nothing
                break;
            case BUS:
                long busID = insertRow(journey.getTransType().getBus(), TAG_ID.MAIN);
                initialValues.put(KEY_TRANSPORT_OBJECT_ID, busID);
                break;
            case SKYTRAIN:
                long trainID = insertRow(journey.getTransType().getSkytrain(), TAG_ID.MAIN);
                initialValues.put(KEY_TRANSPORT_OBJECT_ID, trainID);
                break;
        }

        long buff = db.insert(TABLE_JOURNEY, null, initialValues);
        Log.i(TAG, "[" + TABLE_JOURNEY + "]" + ":" + "insertRow:" + buff + " " + journey.toString());
        // Insert it into the database.
        return buff;
    }

    public long insertRow(Settings s){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_OP_LANG, s.getLanguageMode().ordinal());
        initialValues.put(KEY_OP_MEASUREMENT_UNIT, s.getSillyMode().ordinal());
        long buff = db.insert(TABLE_OPTION, null, initialValues);
        return buff;
    }

    // Return options
    public Settings getSettings(){
        String where = KEY_ROWID + "=" + COL_ROWID;

        Cursor c = db.query(true, TABLE_OPTION, ALL_OP_KEYS,
            where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        int l = c.getInt(COL_OP_LANG);
        int m = c.getInt(COL_OP_MEASUREMENT_UNIT);

        return new Settings(MeasurementUnit.toEnum(m),Language.toEnum(l));
    }

    public boolean updateRow(Settings s){
        String where = KEY_ROWID + "=" + COL_ROWID;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_OP_LANG, s.getLanguageMode().ordinal());
        newValues.put(KEY_OP_MEASUREMENT_UNIT, s.getSillyMode().ordinal());
        boolean bool = false;

        try{
            bool = db.update(TABLE_OPTION, newValues, where, null) != 0;
        } finally {
            insertRow(s);
            bool = true;
        }

        // Insert it into the database.
        return bool;
    }






    /****************************************
     * GETTERS
     * *** STEP 3 Create a getter for object
     * ****************************************/

    // *** Todo: STEP XXXX
    // Return all rows in the table.
    public Cursor getAllRows(DB_TABLE table) {
        String where = null;
        Cursor c = null;
        switch (table) {
            case CAR:
                c = db.query(true, table.toString(), ALL_CAR_KEYS,
                        where, null, null, null, null, null);
                break;
            case ROUTE:
                c = db.query(true, TABLE_ROUTE, ALL_ROUTE_KEYS,
                        where, null, null, null, null, null);
                break;
            case JOURNEY:
                c = db.query(true, TABLE_JOURNEY, ALL_JOURNEY_KEYS,
                        where, null, null, null, null, null);
                break;
            case BILL:
                c = db.query(true, TABLE_BILL, ALL_BILL_KEYS,
                        where, null, null, null, null, null);
                break;
            case BUS:
                c = db.query(true, TABLE_BUS, ALL_BUS_KEYS,
                        where, null, null, null, null, null);
                break;
            case SKYTRAIN:
                c = db.query(true, TABLE_SKYTRAIN, ALL_SKYTRAIN_KEYS,
                        where, null, null, null, null, null);
                break;
            case WALK:
                c = db.query(true, TABLE_WALK, ALL_WALK_KEYS,
                        where, null, null, null, null, null);
                break;
            case BIKE:
                c = db.query(true, TABLE_BIKE, ALL_BIKE_KEYS,
                        where, null, null, null, null, null);
                break;
            case TRANSIT:
                c = db.query(true, TABLE_TRANSIT, ALL_TRANSIT_KEYS,
                        where, null, null, null, null, null);
                break;
        }


        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    //*** Todo: STEP XXXX
    // Get a specific row (by rowId and Table)
    public Cursor getRow(DB_TABLE table, long rowId) {
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = null;
        switch (table) {
            case CAR:
                c = db.query(true, table.toString(), ALL_CAR_KEYS,
                        where, null, null, null, null, null);
                break;
            case ROUTE:
                c = db.query(true, TABLE_ROUTE, ALL_ROUTE_KEYS,
                        where, null, null, null, null, null);
                break;
            case JOURNEY:
                c = db.query(true, TABLE_ROUTE, ALL_JOURNEY_KEYS,
                        where, null, null, null, null, null);
                break;
            case BILL:
                c = db.query(true, TABLE_BILL, ALL_BILL_KEYS,
                        where, null, null, null, null, null);
                break;
            case BUS:
                c = db.query(true, TABLE_BUS, ALL_BUS_KEYS,
                        where, null, null, null, null, null);
                break;
            case SKYTRAIN:
                c = db.query(true, TABLE_SKYTRAIN, ALL_SKYTRAIN_KEYS,
                        where, null, null, null, null, null);
                break;
            case WALK:
                c = db.query(true, TABLE_WALK, ALL_WALK_KEYS,
                        where, null, null, null, null, null);
                break;
            case BIKE:
                c = db.query(true, TABLE_BIKE, ALL_BIKE_KEYS,
                        where, null, null, null, null, null);
                break;
            case TRANSIT:
                c = db.query(true, TABLE_TRANSIT, ALL_TRANSIT_KEYS,
                        where, null, null, null, null, null);
                break;
        }

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // *** STEP 4 Attach getter Journey
    public Journey getJourney(long rowId) {
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = db.query(true, TABLE_JOURNEY, ALL_JOURNEY_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        long ID = c.getLong(COL_ROWID);
        String name = c.getString(COL_JOURNEY_NAME);
        String TRANS_TYPE = c.getString(COL_JOURNEY_TRANS_TYPE);
        String DATE = c.getString(COL_JOURNEY_DATE);
        long ROUTE_ID = c.getInt(COL_JOURNEY_ROUTE_ID);

        // Get and set Route
        Route route = getRoute(ROUTE_ID);

        // Get and set Transport Type
        Transportation transportation = new Transportation();
        transportation.setTransMode(buffTrans(TRANS_TYPE));
        // (1) Get Object
        // (2) Attach id to Transportation Object
        switch (transportation.getTransMode()) {
            case CAR:
                long CAR_ID = c.getInt(COL_TRANSPORT_OBJECT_ID);
                transportation.setCar(getCar(CAR_ID));
                break;
            case BIKE:
                // Do nothing
                break;
            case WALK:
                // Do nothing
                break;
            case BUS:
                long busID = c.getInt(COL_TRANSPORT_OBJECT_ID);
                transportation.setBus(getBus(busID));
                break;
            case SKYTRAIN:
                long trainID = c.getInt(COL_TRANSPORT_OBJECT_ID);
                transportation.setSkytrain(getSkytrain(trainID));
                break;
        }
        Journey j = new Journey(name, transportation, route);
        j.setID(ID);
        j.setDate(DATE);
        j.setImageId(c.getInt(COL_IMG_ID));
        Log.i(TAG, "[" + TABLE_JOURNEY + "]" + ":" + "getJourney:" + rowId + " " + j.toString());
        return j;
    }

    public JourneyCollection getAllJourney() {
        Cursor cursor = getAllRows(DB_TABLE.JOURNEY);
        JourneyCollection jC = new JourneyCollection();
        if (cursor.moveToFirst()) {
            do {
                long ID = cursor.getLong(COL_ROWID);
                Journey j = getJourney(ID);
                jC.addJourney(j);

            } while (cursor.moveToNext());

        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

        return jC;
    }

    public Bill getBill(long rowId) {
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = db.query(true, TABLE_BILL, ALL_BILL_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        Date start_date = new Date(c.getLong(COL_BILL_START_DATE));
        Date end_date = new Date(c.getLong(COL_BILL_END_DATE));
        Double input = c.getDouble(COL_BILL_INPUT);
        BillType type = null;

        int billInt = c.getInt(COL_BILL_TYPE);
        for (BillType t : BillType.values()) {
            if (billInt == t.ordinal()) {
                type = t;
                break;
            }
        }
        return new Bill(type, start_date, end_date, input);
    }

    public List<Bill> getAllBills() {
        Cursor cursor = getAllRows(DB_TABLE.BILL);
        List<Bill> billList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // Fetch objects info from db
                long row = cursor.getLong(COL_ROWID);
                Bill bill = getBill(row);
                billList.add(bill);

            } while (cursor.moveToNext());
        }
        // Close the cursor to avoid a resource leak.
        cursor.close();

        return billList;
    }

    public Route getRoute(long rowId) {
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = db.query(true, TABLE_ROUTE, ALL_ROUTE_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        double cityDistance = c.getDouble(DBAdapter.COL_ROUTE_CITY_DISTANCE);
        double highWayDistance = c.getDouble(DBAdapter.COL_ROUTE_HIGH_WAY_DISTANCE);
        double otherDistance = c.getDouble(DBAdapter.COL_ROUTE_OTHER_DISTANCE);//for bike,walk,bus,skytrain
        int mode = c.getInt(DBAdapter.COL_ROUTE_MODE);//2 for bike and walk,3 for bus, 4 for skytrain
        String name = c.getString(DBAdapter.COL_ROUTE_NAME);
        Route route = new Route(name, highWayDistance, cityDistance, otherDistance, mode);
        route.setImageId(c.getInt(COL_IMG_ID));

        return route;
    }

    public RouteCollection getAllRoute() {
        String message = "";
        // populate the message from the cursor
        // Reset cursor to start, checking to see if there's data:
        RouteCollection rC = new RouteCollection();
        Cursor cursor = getAllRows(DB_TABLE.ROUTE);
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                long row = cursor.getLong(COL_ROWID);
                Route route = getRoute(row);
                rC.addRoute(route);
            } while (cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

        return rC;
    }

    public Car getCar(long rowId) {
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = db.query(true, TABLE_CAR, ALL_CAR_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        Car car = new Car();
        // Process the data:
        double carbonTailPipe = c.getDouble(DBAdapter.COL_CAR_CARBON_TAIL_PIPE);
        double engineDispLitres = c.getDouble(DBAdapter.COL_CAR_ENGINE_DISP_LITRES);
        int cityMPG = c.getInt(DBAdapter.COL_CAR_CITY_MPG);
        int fuelAnnualCost = c.getInt(DBAdapter.COL_CAR_FUEL_ANNUAL_COST);
        int highwayMPG = c.getInt(DBAdapter.COL_CAR_HIGHWAY_MPG);
        int year = c.getInt(DBAdapter.COL_CAR_YEAR);
        String engineDescription = c.getString(DBAdapter.COL_CAR_ENGINE_DESCRIPTION);
        String fuelType = c.getString(DBAdapter.COL_CAR_FUEL_TYPE);
        String make = c.getString(DBAdapter.COL_CAR_MAKE);
        String model = c.getString(DBAdapter.COL_CAR_MODEL);
        String nickName = c.getString(DBAdapter.COL_CAR_NICK_NAME);
        String transDescription = c.getString(DBAdapter.COL_CAR_TRANS_DESCRIPTION);
        car.setCarbonTailPipe(carbonTailPipe);
        car.setEngineDispLitres(engineDispLitres);
        car.setCityMPG(cityMPG);
        car.setFuelAnnualCost(fuelAnnualCost);
        car.setHighwayMPG(highwayMPG);
        car.setYear(year);
        car.setEngineDescription(engineDescription);
        car.setFuelType(fuelType);
        car.setMake(make);
        car.setModel(model);
        car.setNickName(nickName);
        car.setTransDescription(transDescription);
        car.setID(rowId);
        car.setImageId(c.getInt(COL_IMG_ID));

        return car;
    }

    public CarCollection getAllCars() {
        String message = "";
        // populate the message from the cursor

        CarCollection cC = new CarCollection();
        Cursor cursor = getAllRows(DB_TABLE.CAR);

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                long row = cursor.getLong(COL_ROWID);
                Car car = getCar(row);
                car.setID(row);
                cC.add(car);
            } while (cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();
        return cC;
    }

    public CarCollection getAllCars(TAG_ID tag_id) { // Get All Cars with a certain tag_id
        CarCollection cC = new CarCollection();
        Cursor cursor = getAllRows(DB_TABLE.CAR);

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                int this_tag_id = cursor.getInt(COL_CAR_TAG_ID);
                if (tag_id.ordinal() == this_tag_id) {
                    long row = cursor.getLong(COL_ROWID);
                    Car car = getCar(row);
                    cC.add(car);
                    Log.i(TAG, "[" + DB_TABLE.CAR + "]" + ":" + "getCar:" + row + " " + tag_id + ": " + car.toString());
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();
        return cC;
    }

    public Bus getBus(long rowId) {
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = db.query(true, TABLE_BUS, ALL_BUS_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        Bus bus = new Bus();

        bus.setNickName(c.getString(COL_BUS_NICK_NAME));
        bus.setRouteNumber(c.getString(COL_BUS_ROUTE_NUMBER));
        bus.setImageId(c.getInt(COL_IMG_ID));

        return bus;
    }

    public BusCollection getAllBus() {
        BusCollection bC = new BusCollection();
        Cursor cursor = getAllRows(DB_TABLE.BUS);
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                long rowId = cursor.getLong(COL_ROWID);
                Bus bus = getBus(rowId);
                bC.addBus(bus);
            } while (cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();
        return bC;
    }

    public BusCollection getAllBus(TAG_ID tag_id) {
        BusCollection bC = new BusCollection();
        Cursor cursor = getAllRows(DB_TABLE.BUS);
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                int this_tag = cursor.getInt(COL_BUS_TAG_ID);
                if (this_tag == tag_id.ordinal()) {
                    long rowId = cursor.getLong(COL_ROWID);
                    Bus bus = getBus(rowId);
                    bC.addBus(bus);
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();
        return bC;
    }


    public Skytrain getSkytrain(long rowId) {
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = db.query(true, TABLE_SKYTRAIN, ALL_SKYTRAIN_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        Skytrain train = new Skytrain();
        train.setNickName(c.getString(COL_SKYTRAIN_NICK_NAME));
        train.setSkytrainLine(c.getString(COL_SKYTRAIN_LINE));
        train.setBoardingStation(c.getString(COL_SKYTRAIN_BOARDING_STATION));
        train.setImageId(c.getInt(COL_IMG_ID));

        return train;
    }

    public SkytrainCollection getAllSkytrain(TAG_ID tag_id) {
        SkytrainCollection sC = new SkytrainCollection();
        Cursor cursor = getAllRows(DB_TABLE.SKYTRAIN);
        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                int this_tag = cursor.getInt(COL_SKYTRAIN_TAG_ID);
                if (this_tag == tag_id.ordinal()) {
                    long rowId = cursor.getLong(COL_ROWID);
                    Skytrain train = getSkytrain(rowId);
                    sC.addTrain(train);
                }
            } while (cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();
        return sC;
    }


    // Todo: Unused and unfunctional atm
    // Change an existing row to be equal to new data.
    public boolean updateRow(Car car) {
        long rowId = car.getID();
        String where = KEY_ROWID + "=" + rowId;

		/*
         * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_CAR_CARBON_TAIL_PIPE, car.getCarbonTailPipe());
        newValues.put(KEY_CAR_CITY_MPG, car.getCityMPG());
        newValues.put(KEY_CAR_ENGINE_DESCRIPTION, car.getEngineDescription());
        newValues.put(KEY_CAR_ENGINE_DISP_LITRES, car.getEngineDispLitres());
        newValues.put(KEY_CAR_FUEL_ANNUAL_COST, car.getFuelAnnualCost());
        newValues.put(KEY_CAR_FUEL_TYPE, car.getFuelType());
        newValues.put(KEY_CAR_HIGHWAY_MPG, car.getHighwayMPG());
        newValues.put(KEY_CAR_MAKE, car.getMake());
        newValues.put(KEY_CAR_MODEL, car.getModel());
        newValues.put(KEY_CAR_NICK_NAME, car.getNickName());
        newValues.put(KEY_CAR_TRANS_DESCRIPTION, car.getTransDescription());
        newValues.put(KEY_CAR_YEAR, car.getYear());

        // Insert it into the database.
        return db.update(TABLE_CAR, newValues, where, null) != 0;
    }

    public boolean updateRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();


//        newValues.put(KEY_NAME, name);
//        newValues.put(KEY_STUDENTNUM, studentNum);
//        newValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.update(DATABASE_NAME, newValues, where, null) != 0;
    }

    /****************************************
     * PRIVATE HELPER STUFF
     * ****************************************/

    // Todo should pass in enum ordinal instead of string...
    private Transport buffTrans(String trans) {
        switch (trans) {
            case "Car":
                return Transport.CAR;
            case "Walk":
                return Transport.WALK;
            case "Bike":
                return Transport.BIKE;
            case "Bus":
                return Transport.BUS;
            case "SkyTrain":
                return Transport.SKYTRAIN;
            case "Transit":
                return Transport.TRANSIT;
            default:
                assert false;
        }
        return null;
    }

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
//            _db.execSQL(DATABASE_CREATE_SQL);
            _db.execSQL(CREATE_TABLE_ROUTE);
            _db.execSQL(CREATE_TABLE_CAR);
            _db.execSQL(CREATE_TABLE_JOURNEY);
            _db.execSQL(CREATE_TABLE_BUS);
            _db.execSQL(CREATE_TABLE_SKYTRAIN);
            _db.execSQL(CREATE_TABLE_BILL);
            _db.execSQL(CREATE_TABLE_OPTION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
//            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTE);
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNEY);
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUS);
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKYTRAIN);
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTION);

            // Recreate new database:
            onCreate(_db);
        }
    }

    public static void save(Context ctx, Journey journey) {
        DBAdapter db = new DBAdapter(ctx);
        db.open();
        db.insertRow(journey);
        db.close();
    }

    public static void delete(Context ctx, Journey journey) {
        DBAdapter db = new DBAdapter(ctx);
        db.open();
        db.deleteJourney(journey);
        db.close();
    }

    public static void save(Context ctx, Bill bill) {
        DBAdapter db = new DBAdapter(ctx);
        db.open();
        db.insertRow(bill);
        db.close();
    }


    /**
     * Created by tangj on 3/20/2017.
     */

    public static enum DB_TABLE {
        CAR(DBAdapter.TABLE_CAR), ROUTE(DBAdapter.TABLE_ROUTE), JOURNEY(DBAdapter.TABLE_JOURNEY),
        BILL(DBAdapter.TABLE_BILL), BUS(DBAdapter.TABLE_BUS), SKYTRAIN(DBAdapter.TABLE_SKYTRAIN),
        WALK(DBAdapter.TABLE_WALK), BIKE(DBAdapter.TABLE_BIKE), TRANSIT(DBAdapter.TABLE_TRANSIT);

        private String name;

        private DB_TABLE(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Differential Between a Transport in Journey and Recent Transport
    public static enum TAG_ID {
        RECENT("Recent"), MAIN("Main");

        private String name;

        private TAG_ID(String name) {
            this.name = name;
        }
    }
}