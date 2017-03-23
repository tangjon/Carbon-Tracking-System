package cmpt276.jade.carbontracker.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cmpt276.jade.carbontracker.enums.Transport;
import cmpt276.jade.carbontracker.model.Bill;
import cmpt276.jade.carbontracker.model.Bus;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.CarCollection;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.JourneyCollection;
import cmpt276.jade.carbontracker.model.Route;
import cmpt276.jade.carbontracker.model.RouteCollection;
import cmpt276.jade.carbontracker.model.Skytrain;
import cmpt276.jade.carbontracker.model.Transportation;
import cmpt276.jade.carbontracker.model.Utilities;
import cmpt276.jade.carbontracker.utils.BillType;

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
    public static final int DATABASE_VERSION = 22;

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";

    // DB Tables
    public static final String DATABASE_TABLE = "mainTable";

    public static final String TABLE_ROUTE = "routes";

    // TRANSPORT TABLES
    public static final String TABLE_CAR = "cars";
    public static final String TABLE_JOURNEY = "journeys";
    public static final String TABLE_BUS = "buss";
    public static final String TABLE_SKYTRAIN = "sktrains";
    public static final String TABLE_WALK = "walks";
    public static final String TABLE_BIKE = "bikes";
    public static final String TABLE_TRANSIT = "transits";

    // UTILITY TABLES
    public static final String TABLE_BILL = "bills";

    // General DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    // TODO: Setup Journey Fields Here
    public static final String KEY_JOURNEY_NAME = "journey_name";
    public static final String KEY_JOURNEY_TRANS_TYPE = "journey_trans_type";
    public static final String KEY_JOURNEY_DATE = "journey_date";
    public static final String KEY_JOURNEY_ROUTE_ID = "journey_route";
    public static final String KEY_TRANSPORT_OBJECT_ID = "journey_car";
    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    public static final int COL_JOURNEY_NAME = 1;
    public static final int COL_JOURNEY_TRANS_TYPE = 2;
    public static final int COL_JOURNEY_DATE = 3;
    public static final int COL_JOURNEY_ROUTE_ID = 4;
    public static final int COL_TRANSPORT_OBJECT_ID = 5;
    // ALL KEYS
    public static final String[] ALL_JOURNEY_KEYS = new String[] {
            KEY_ROWID,
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

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    public static final int COL_CAR_CARBON_TAIL_PIPE = 1;
    public static final int COL_CAR_CITY_MPG = 2;
    public static final int COL_CAR_ENGINE_DESCRIPTION = 3;
    public static final int COL_CAR_ENGINE_DISP_LITRES = 4;
    public static final int COL_CAR_FUEL_ANNUAL_COST = 5;
    public static final int COL_CAR_FUEL_TYPE = 6;
    public static final int COL_CAR_HIGHWAY_MPG = 7;
    public static final int COL_CAR_MAKE = 8;
    public static final int COL_CAR_MODEL = 9;
    public static final int COL_CAR_NICK_NAME = 10;
    public static final int COL_CAR_TRANS_DESCRIPTION = 11;
    public static final int COL_CAR_YEAR = 12;

    // ALL KEYS (Contains all KEYS in array of strings) (DETERMINES THE COLUMNS NUMBERS)
    public static final String[] ALL_CAR_KEYS = new String[] {
            KEY_ROWID,
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
            KEY_CAR_YEAR };

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_CAR =
            "create table " + TABLE_CAR
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // TODO: Place your fields here!
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
                    + KEY_CAR_YEAR + " integer"

                    // Rest  of creation:
                    + ");";



    // TODO: Setup Route Fields Here

    public static final String KEY_ROUTE_CITY_DISTANCE = "city_distance";
    public static final String KEY_ROUTE_HIGH_WAY_DISTANCE = "high_way_distance";
    public static final String KEY_ROUTE_OTHER_DISTANCE = "other_distance" ;
    public static final String KEY_ROUTE_MODE = "mode";
    public static final String KEY_ROUTE_NAME = "name";
    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    public static final int COL_ROUTE_CITY_DISTANCE = 1;
    public static final int COL_ROUTE_HIGH_WAY_DISTANCE = 2;
    public static final int COL_ROUTE_OTHER_DISTANCE = 3;
    public static final int COL_ROUTE_MODE = 4;
    public static final int COL_ROUTE_NAME = 5;
    // ALL KEYS
    public static final String[] ALL_ROUTE_KEYS = new String[] {
            KEY_ROWID,
            KEY_ROUTE_CITY_DISTANCE,
            KEY_ROUTE_HIGH_WAY_DISTANCE,
            KEY_ROUTE_OTHER_DISTANCE,
            KEY_ROUTE_MODE,
            KEY_ROUTE_NAME
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
                    +KEY_ROUTE_CITY_DISTANCE+" real,"
                    +KEY_ROUTE_HIGH_WAY_DISTANCE+" real,"
                    +KEY_ROUTE_OTHER_DISTANCE+" real,"
                    +KEY_ROUTE_MODE+" integer,"
                    +KEY_ROUTE_NAME+" text"
                    // Rest  of creation:
                    + ");";

    // TODO: Setup Bus Fields Here
    private static final String KEY_BUS_NICK_NAME = "bus_nick_name";
    private static final String KEY_BUS_ROUTE_NUMBER = "bus_route_number";

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    public static final int COL_BUS_NICK_NAME = 1;
    public static final int COL_BUS_ROUTE_NUMBER = 2;

    // ALL KEYS
    public static final String[] ALL_BUS_KEYS = new String[] {
            KEY_ROWID,
            KEY_BUS_NICK_NAME,
            KEY_BUS_ROUTE_NUMBER
    };

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_BUS =
            "create table " + TABLE_BUS
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // TODO: Place your fields here!
                    + KEY_BUS_NICK_NAME + " text, "
                    + KEY_BUS_ROUTE_NUMBER + " text"

                    // Rest  of creation:
                    + ");";

    // TODO: Setup Skytrain Fields Here
    private static final String KEY_SKYTRAIN_NICK_NAME = "skytrain_nick_name";
    private static final String KEY_SKYTRAIN_BOARDING_STATION = "skytrain_boarding_station";
    private static final String KEY_SKYTRAIN_LINE = "key_skytrain_line";
    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    private static final int COL_SKYTRAIN_NICK_NAME = 1;
    private static final int COL_SKYTRAIN_BOARDING_STATION = 2;
    private static final int COL_SKYTRAIN_LINE = 3;
    // ALL KEYS
    public static final String[] ALL_SKYTRAIN_KEYS = new String[] {
            KEY_ROWID,
            KEY_SKYTRAIN_NICK_NAME,
            KEY_SKYTRAIN_BOARDING_STATION,
            KEY_SKYTRAIN_LINE };

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_SKYTRAIN =
            "create table " + TABLE_SKYTRAIN
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // TODO: Place your fields here!
                    + KEY_SKYTRAIN_NICK_NAME + " text, "
                    + KEY_SKYTRAIN_BOARDING_STATION + " text, "
                    + KEY_SKYTRAIN_LINE + " text"

                    // Rest  of creation:
                    + ");";



    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    // ALL KEYS
    public static final String[] ALL_WALK_KEYS = new String[] {
    };
    // Create the Data Base (SQL)

    // TODO: Setup Bike Fields Here
    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    // ALL KEYS
    public static final String[] ALL_BIKE_KEYS = new String[] {
    };
    // Create the Data Base (SQL)
    // TODO: Setup Transit Fields Here
    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    // ALL KEYS
    public static final String[] ALL_TRANSIT_KEYS = new String[] {
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
    public static final String[] ALL_BILL_KEYS = new String[] {
            KEY_ROWID,
            KEY_BILL_TYPE,
            KEY_BILL_START_DATE,
            KEY_BILL_END_DATE,
            KEY_BILL_INPUT};

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_BILL =
            "create table " + TABLE_BILL
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // TODO: Place your fields here!
                    + KEY_BILL_TYPE + " int, "
                    + KEY_BILL_START_DATE + " int, "
                    + KEY_BILL_END_DATE + " int, "
                    + KEY_BILL_INPUT + " real"

                    // Rest  of creation:
                    + ");";


    ///////////////////////////
    // FOR REFERENCE
    ///////////////////////////

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

    // *********************
    // GENERAL FUNCTIONS
    // *********************
    //Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(DB_TABLE table, long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(table.toString(), where, null) != 0;
    }

    // [DONE]
    public void deleteAll(DB_TABLE table) {
        Cursor c = getAllRows(table);
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(table,c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        if(table.equals(DB_TABLE.JOURNEY)){
            deleteAll(DB_TABLE.CAR);
            deleteAll(DB_TABLE.ROUTE);
            deleteAll(DB_TABLE.SKYTRAIN);
            deleteAll(DB_TABLE.BUS);
        }
        c.close();
    }


    // [UPDATE]
    // Return all data in the database.
    public Cursor getAllRows(DB_TABLE table) {
        String where = null;
        Cursor c = null;
        switch(table){
            case CAR:
                c =	db.query(true, table.toString(), ALL_CAR_KEYS,
                        where, null, null, null, null, null);
                break;
            case ROUTE:
                c =	db.query(true, TABLE_ROUTE, ALL_ROUTE_KEYS,
                        where, null, null, null, null, null);
                break;
            case JOURNEY:
                c =	db.query(true, TABLE_JOURNEY, ALL_JOURNEY_KEYS,
                        where, null, null, null, null, null);
                break;
            case BILL:
                c =	db.query(true, TABLE_BILL, ALL_BILL_KEYS,
                        where, null, null, null, null, null);
                break;
            case BUS:
                c =	db.query(true, TABLE_BUS, ALL_BUS_KEYS,
                        where, null, null, null, null, null);
                break;
            case SKYTRAIN:
                c =	db.query(true, TABLE_SKYTRAIN, ALL_SKYTRAIN_KEYS,
                        where, null, null, null, null, null);
                break;
            case WALK:
                c =	db.query(true, TABLE_WALK, ALL_WALK_KEYS,
                        where, null, null, null, null, null);
                break;
            case BIKE:
                c =	db.query(true, TABLE_BIKE, ALL_BIKE_KEYS,
                        where, null, null, null, null, null);
                break;
            case TRANSIT:
                c =	db.query(true, TABLE_TRANSIT, ALL_TRANSIT_KEYS,
                        where, null, null, null, null, null);
                break;
        }


        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // *********************
    // INSERT FUNCTIONS
    // *** STEP 1 create a insert function
    // *********************

    /* [DONE] */
    public long insertRow(Car car) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CAR_CARBON_TAIL_PIPE,car.getCarbonTailPipe());
        initialValues.put(KEY_CAR_CITY_MPG,car.getCityMPG());
        initialValues.put(KEY_CAR_ENGINE_DESCRIPTION,car.getEngineDescription());
        initialValues.put(KEY_CAR_ENGINE_DISP_LITRES,car.getEngineDispLitres());
        initialValues.put(KEY_CAR_FUEL_ANNUAL_COST,car.getFuelAnnualCost());
        initialValues.put(KEY_CAR_FUEL_TYPE,car.getFuelType());
        initialValues.put(KEY_CAR_HIGHWAY_MPG,car.getHighwayMPG());
        initialValues.put(KEY_CAR_MAKE,car.getMake());
        initialValues.put(KEY_CAR_MODEL,car.getModel());
        initialValues.put(KEY_CAR_NICK_NAME,car.getNickName());
        initialValues.put(KEY_CAR_TRANS_DESCRIPTION,car.getTransDescription());
        initialValues.put(KEY_CAR_YEAR,car.getYear());

        // Insert it into the database.
        return db.insert(TABLE_CAR, null, initialValues);
    }
    /* [DONE] */
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

        Log.i(TAG, "insertRowBill: " + bill.getStartDate().getTime());
        // Insert it into the database.
        return db.insert(TABLE_BILL, null, initialValues);
    }

    /* [DONE] */
    public long insertRow(Route route) {
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROUTE_NAME, route.getName() );
        initialValues.put(KEY_ROUTE_HIGH_WAY_DISTANCE, route.getHighWayDistance() );
        initialValues.put(KEY_ROUTE_CITY_DISTANCE, route.getCityDistance() );
        initialValues.put(KEY_ROUTE_OTHER_DISTANCE, route.getOtherDistance() );
        initialValues.put(KEY_ROUTE_MODE, route.getMode() );
        // Insert it into the database.
        return db.insert(TABLE_ROUTE, null, initialValues);
    }

    // [DONE]
    public long insertRow(Bus bus) {
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BUS_NICK_NAME, bus.getNickName() );
        initialValues.put(KEY_BUS_ROUTE_NUMBER, bus.getRouteNumber() );
        // Insert it into the database.
        return db.insert(TABLE_BUS, null, initialValues);
    }

    public long insertRow(Skytrain skytrain) {
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SKYTRAIN_NICK_NAME, skytrain.getNickName() );
        initialValues.put(KEY_SKYTRAIN_BOARDING_STATION, skytrain.getBoardingStation() );
        initialValues.put(KEY_SKYTRAIN_LINE, skytrain.getSkytrainLine() );
        // Insert it into the database.
        return db.insert(TABLE_SKYTRAIN, null, initialValues);
    }
    // *** STEP 2 ATTACH TO JOURNEY TABLE
    public long insertRow(Journey journey) {
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        ContentValues initialValues = new ContentValues();

        // Insert Journey Info
        initialValues.put(KEY_JOURNEY_NAME, journey.getName() );
        initialValues.put(KEY_JOURNEY_TRANS_TYPE, journey.getTransType().getTransMode().toString());
        initialValues.put(KEY_JOURNEY_DATE, journey.getDate());

        // Insert Route
        long routeID = insertRow(journey.getRoute());
        initialValues.put(KEY_JOURNEY_ROUTE_ID, routeID);

        // Insert Transportation Object
        // (1) Insert Object
        // (2) Attach id to KEY_TRANSPORT_OBJECT_ID
        Transport mode = journey.getTransType().getTransMode();
        switch (mode){
            case CAR:
                // Insert Car
                long carID = insertRow(journey.getTransType().getCar());
                initialValues.put(KEY_TRANSPORT_OBJECT_ID, carID);
                break;
            case BIKE:
                // Do nothing
                break;
            case WALK:
                // Do nothing
                break;
            case BUS:
                long busID = insertRow(journey.getTransType().getBus());
                initialValues.put(KEY_TRANSPORT_OBJECT_ID, busID);
                break;
            case SKYTRAIN:
                long trainID = insertRow(journey.getTransType().getSkytrain());
                initialValues.put(KEY_TRANSPORT_OBJECT_ID, trainID);
                break;
        }

        Log.i(TAG, "insertRow: " + journey.toString());
        // Insert it into the database.
        return db.insert(TABLE_JOURNEY, null, initialValues);
    }




    // ==========================
    // GETTERS
    // *** STEP 3 Create a getter for object
    // ==========================
    // ***************************************

    // *** STEP 4 Attach getter Journey
    public Journey getJourney(long rowId){
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = db.query(true, TABLE_JOURNEY, ALL_JOURNEY_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

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
        switch (transportation.getTransMode()){
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
        Journey j = new Journey(name, transportation,route);
        j.setDate(DATE);
        Log.i(TAG, "getJourney: " + j.toString());
        // Todo set date
        return j;
    }

    public JourneyCollection getAllJourney(){
        Cursor cursor =  getAllRows(DB_TABLE.JOURNEY);
        JourneyCollection jC = new JourneyCollection();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(COL_JOURNEY_NAME);
                String TRANS_TYPE = cursor.getString(COL_JOURNEY_TRANS_TYPE);
                String DATE = cursor.getString(COL_JOURNEY_DATE);
                long ROUTE_ID = cursor.getInt(COL_JOURNEY_ROUTE_ID);

                Log.i(TAG, "getAllJourney: " + name);

                // Get and set Route
                Route route = getRoute(ROUTE_ID);

                // Get and set Transport Type
                Transportation transportation = new Transportation();
                transportation.setTransMode(buffTrans(TRANS_TYPE));
                // (1) Get Object
                // (2) Attach id to Transportation Object
                switch (transportation.getTransMode()){
                    case CAR:
                        long CAR_ID = cursor.getInt(COL_TRANSPORT_OBJECT_ID);
                        transportation.setCar(getCar(CAR_ID));
                        break;
                    case BIKE:
                        // Do nothing
                        break;
                    case WALK:
                        // Do nothing
                        break;
                    case BUS:
                        long busID = cursor.getInt(COL_TRANSPORT_OBJECT_ID);
                        transportation.setBus(getBus(busID));
                        break;
                    case SKYTRAIN:
                        long trainID = cursor.getInt(COL_TRANSPORT_OBJECT_ID);
                        transportation.setSkytrain(getSkytrain(trainID));
                        break;
                }
                Journey j = new Journey(name, transportation,route);
                j.setDate(DATE);
                Log.i(TAG, "getAllJourney: " + j.toString());
                jC.addJourney(j);

            } while(cursor.moveToNext());

        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

        return jC;
    }

    public List<Bill> getAllBills(){
        Cursor cursor =  getAllRows(DB_TABLE.BILL);
        List<Bill> billList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // Fetch objects info from db
                Date start_date = new Date(cursor.getLong(COL_BILL_START_DATE));
                Date end_date = new Date(cursor.getInt(COL_BILL_END_DATE));
                Double input = cursor.getDouble(COL_BILL_INPUT);
                BillType type = null;

                int billInt = cursor.getInt(COL_BILL_TYPE);
                for (BillType t: BillType.values()) {
                    if(billInt == t.ordinal()){
                        type = t;
                        break;
                    }
                }
                Bill bill = new Bill(type, start_date,end_date,input);
                billList.add(bill);

            } while(cursor.moveToNext());

        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

        return billList;
    }

    public RouteCollection getAllRoute(){
        String message = "";
        // populate the message from the cursor
        // Reset cursor to start, checking to see if there's data:
        RouteCollection rC = new RouteCollection();
        Cursor cursor =  getAllRows(DB_TABLE.ROUTE);
        if (cursor.moveToFirst()) {
            do {
                // Process the data:

                double cityDistance = cursor.getDouble(DBAdapter.COL_ROUTE_CITY_DISTANCE);
                double highWayDistance = cursor.getDouble(DBAdapter.COL_ROUTE_HIGH_WAY_DISTANCE);
                double otherDistance = cursor.getDouble(DBAdapter.COL_ROUTE_OTHER_DISTANCE);//for bike,walk,bus,skytrain
                int mode = cursor.getInt(DBAdapter.COL_ROUTE_MODE);//2 for bike and walk,3 for bus, 4 for skytrain
                String name = cursor.getString(DBAdapter.COL_ROUTE_NAME);

                // Append data to the message:
                message += "name=" + name
                        +", CityDistance=" + cityDistance
                        +", HighWayDistance=" + highWayDistance
                        +", OtherDistance=" + otherDistance
                        +", mode=" + mode
                        +"\n";

                rC.addRoute(new Route(name,highWayDistance,cityDistance,otherDistance,mode));
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

//        Log.i(TAG, "displayRecordSetForRoute: " + message);
        return rC;
    }

    public CarCollection getAllCar(){
        String message = "";
        // populate the message from the cursor

        CarCollection cC = new CarCollection();
        Cursor cursor =  getAllRows(DB_TABLE.CAR);

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

                cC.add(new Car(nickName,make,model,year,cityMPG,highwayMPG,
                        engineDescription,engineDispLitres,fuelType,fuelAnnualCost,carbonTailPipe,transDescription));
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();

//        Log.i(TAG, "displayRecordSetForCar: " + message);
        return cC;
    }

    // [POSSIBLE REFACTOR]
    public Object getObject(DB_TABLE t, long rowId){
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = null;
        switch (t){
            case CAR:
                break;
            case ROUTE:
                c = db.query(true, TABLE_ROUTE, ALL_ROUTE_KEYS,
                        where, null, null, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                }
                double cityDistance = c.getDouble(DBAdapter.COL_ROUTE_CITY_DISTANCE);
                double highWayDistance = c.getDouble(DBAdapter.COL_ROUTE_HIGH_WAY_DISTANCE);
                double otherDistance = c.getDouble(DBAdapter.COL_ROUTE_OTHER_DISTANCE);//for bike,walk,bus,skytrain
                int mode = c.getInt(DBAdapter.COL_ROUTE_MODE);//2 for bike and walk,3 for bus, 4 for skytrain
                String name = c.getString(DBAdapter.COL_ROUTE_NAME);

                return new Route(name, highWayDistance, cityDistance, otherDistance, mode);
            case JOURNEY:
                break;
            case BILL:
                break;
            case BUS:
                break;
            case SKYTRAIN:
                break;
            case WALK:
                break;
            case BIKE:
                break;
            case TRANSIT:
                break;
        }
        return null;
    }
    // [DONE]
    public Route getRoute(long rowId){
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

        return new Route(name, highWayDistance, cityDistance, otherDistance, mode);
    }
    // [DONE]
    public Car getCar(long rowId){
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
        int fuelAnnualCost= c.getInt(DBAdapter.COL_CAR_FUEL_ANNUAL_COST);
        int highwayMPG = c.getInt(DBAdapter.COL_CAR_HIGHWAY_MPG);
        int year = c.getInt(DBAdapter.COL_CAR_YEAR);
        String engineDescription = c.getString(DBAdapter.COL_CAR_ENGINE_DESCRIPTION);
        String fuelType = c.getString(DBAdapter.COL_CAR_FUEL_TYPE);
        String make = c.getString(DBAdapter.COL_CAR_MAKE);
        String model = c.getString(DBAdapter.COL_CAR_MODEL);
        String nickName = c.getString(DBAdapter.COL_CAR_NICK_NAME);
        String transDescription= c.getString(DBAdapter.COL_CAR_TRANS_DESCRIPTION);
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

//        String message = "";
        // populate the message from the cursor
//        // Append data to the message:
//        message += "make=" + make
//                +", model=" + model
//                +", cityMPG=" + cityMPG
//                +", fuelType=" + fuelType
//                +"\n";


        return car;
    }
    // [DONE]
    public Bus getBus(long rowId){
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = db.query(true, TABLE_BUS, ALL_BUS_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        Bus bus = new Bus();

        bus.setNickName(c.getString(COL_BUS_NICK_NAME));
        bus.setRouteNumber(c.getString(COL_BUS_ROUTE_NUMBER));

        return bus;
    }
    // [DONE]
    public Skytrain getSkytrain(long rowId){
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

        return train;
    }

    // Todo getBill
    public Bill getBill(long rowId){
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = db.query(true, TABLE_BILL, ALL_BILL_KEYS,
                where, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        Date start_date = new Date(c.getLong(COL_BILL_START_DATE));
        Date end_date = new Date(c.getInt(COL_BILL_END_DATE));
        Double input = c.getDouble(COL_BILL_INPUT);
        BillType type = null;

        int billInt = c.getInt(COL_BILL_TYPE);
        for (BillType t: BillType.values()) {
            if(billInt == t.ordinal()){
                type = t;
                break;
            }
        }
        return new Bill(type, start_date,end_date,input);
    }

    public Transport buffTrans(String trans){
        switch (trans){
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
    // [UPDATE]
    // Get a specific row (by rowId)
    public Cursor getRow(DB_TABLE table,long rowId) {
        String where = KEY_ROWID + "=" + rowId;

        Cursor c = null;
        switch(table){
            case CAR:
                c =	db.query(true, table.toString(), ALL_CAR_KEYS,
                        where, null, null, null, null, null);
                break;
            case ROUTE:
                c =	db.query(true, TABLE_ROUTE, ALL_ROUTE_KEYS,
                        where, null, null, null, null, null);
                break;
            case JOURNEY:
                c =	db.query(true, TABLE_ROUTE, ALL_JOURNEY_KEYS,
                        where, null, null, null, null, null);
                break;
            case BILL:
                c =	db.query(true, TABLE_BILL, ALL_BILL_KEYS,
                        where, null, null, null, null, null);
                break;
            case BUS:
                c =	db.query(true, TABLE_BUS, ALL_BUS_KEYS,
                        where, null, null, null, null, null);
                break;
            case SKYTRAIN:
                c =	db.query(true, TABLE_SKYTRAIN, ALL_SKYTRAIN_KEYS,
                        where, null, null, null, null, null);
                break;
            case WALK:
                c =	db.query(true, TABLE_WALK, ALL_WALK_KEYS,
                        where, null, null, null, null, null);
                break;
            case BIKE:
                c =	db.query(true, TABLE_BIKE, ALL_BIKE_KEYS,
                        where, null, null, null, null, null);
                break;
            case TRANSIT:
                c =	db.query(true, TABLE_TRANSIT, ALL_TRANSIT_KEYS,
                        where, null, null, null, null, null);
                break;
        }

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // UNTOUCHED
    // Change an existing row to be equal to new data.
//    public boolean updateRow(long rowId, String name, int studentNum, String favColour) {
//        String where = KEY_ROWID + "=" + rowId;
//
//		/*
//		 * CHANGE 4:
//		 */
//        // TODO: Update data in the row with new fields.
//        // TODO: Also change the function's arguments to be what you need!
//        // Create row's data:
//        ContentValues newValues = new ContentValues();
//        newValues.put(KEY_NAME, name);
//        newValues.put(KEY_STUDENTNUM, studentNum);
//        newValues.put(KEY_FAVCOLOUR, favColour);
//
//        // Insert it into the database.
//        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
//    }


    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
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

            // Recreate new database:
            onCreate(_db);
        }
    }

    public static void save(Context ctx, Journey journey){
        DBAdapter db = new DBAdapter(ctx);
        db.open();
        db.insertRow(journey);
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
        public String toString(){
            return name;
        }
    }
}