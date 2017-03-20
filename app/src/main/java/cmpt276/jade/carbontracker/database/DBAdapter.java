package cmpt276.jade.carbontracker.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.Route;

// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // Common DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_NAME = "name";
    public static final String KEY_STUDENTNUM = "studentnum";
    public static final String KEY_FAVCOLOUR = "favcolour";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_NAME = 1;
    public static final int COL_STUDENTNUM = 2;
    public static final int COL_FAVCOLOUR = 3;


    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_STUDENTNUM, KEY_FAVCOLOUR};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";

    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 2;


    // ========
    // TABLE JOURNEY FIELDS
    // ========
    public static final String KEY_JOURNEY_NAME = "journey_name";
    // Car
    public static final String KEY_CAR_NAME = "car_name";
    public static final String KEY_transDescription = "car_descrip";
    public static final String KEY_make = "car_make ";
    public static final String KEY_model = "car_model";
    public static final String KEY_nickName = "car_nick_name";
    public static final String KEY_year = "car_year";
    public static final String KEY_cityMPG = "car_city_mpg";
    public static final String KEY_highwayMPG = "car_highway_mpg";
    public static final String KEY_engineDescription = "car_engine_descrip";
    public static final String KEY_engineDispLitres = "car_engine_displacement";
    public static final String KEY_fuelType = "car_fuel_type";
    public static final String KEY_fuelAnnualCost = "car_fuel_anual_cost";
    public static final String KEY_carbonTailPipe = "car_carbon_tail_pipe";
    // Route
    public static final String KEY_name = "route_name";
    public static final String KEY_HighWayDistance = "route_city_distance";
    public static final String KEY_CityDistance= "route_city_distance";
    public static final String KEY_OtherDistance = "route_other_distance";//for bike,walk,bus,skytrain
    public static final String KEY_mode = "route_mode";//2 for bike and walk,3 for bus, 4 for skytrain

    public static final String[] ALL_JOURNEY_KEYS = new String[] {KEY_JOURNEY_NAME,KEY_CAR_NAME ,KEY_transDescription ,KEY_make ,
        KEY_model ,KEY_nickName ,KEY_year ,KEY_cityMPG ,KEY_highwayMPG ,KEY_engineDescription ,
        KEY_engineDispLitres ,KEY_fuelType ,KEY_fuelAnnualCost ,KEY_carbonTailPipe, KEY_name ,
        KEY_HighWayDistance ,KEY_CityDistance ,KEY_OtherDistance ,KEY_mode};

    public static final int COL_KEY_JOURNEY_NAME = 19;
    public static final int COL_KEY_CAR_NAME = 1;
    public static final int COL_KEY_transDescription = 2;
    public static final int COL_KEY_make = 3;
    public static final int COL_KEY_model = 4;
    public static final int COL_KEY_nickName = 5;
    public static final int COL_KEY_year = 6;
    public static final int COL_KEY_cityMPG = 7;
    public static final int COL_KEY_highwayMPG = 8;
    public static final int COL_KEY_engineDescription = 9;
    public static final int COL_KEY_engineDispLitres= 10;
    public static final int COL_KEY_fuelType = 11;
    public static final int COL_KEY_fuelAnnualCost = 12;
    public static final int COL_KEY_carbonTailPipe = 13;
    public static final int COL_KEY_name = 14;
    public static final int COL_KEY_HighWayDistance = 15;
    public static final int COL_KEY_CityDistance = 16;
    public static final int COL_KEY_OtherDistance = 17;
    public static final int COL_KEY_mode = 18;


    // ===========
    // TABLE NAMES
    // ===========
    public static final String TABLE_JOURNEY = "journeys";
    public static final String TABLE_UTILITY = "utilities";

    // ===========
    // TABLE CREATE FIELDS
    // ===========
    // Create Journey Table Statement
    private static final String CREATE_TABLE_JOURNEY =
            "create table " + TABLE_JOURNEY
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
                + KEY_CAR_NAME + " text not null, "
                + KEY_transDescription + " text not null, "
                + KEY_make + " text not null, "
                + KEY_model + " text not null, "
                + KEY_nickName + " text not null, "
                + KEY_year + " integer not null, "
                + KEY_cityMPG + " real not null, "
                + KEY_highwayMPG + " real not null, "
                + KEY_engineDescription + " text not null, "
                + KEY_engineDispLitres + " real not null, "
                + KEY_fuelType + " text not null, "
                + KEY_fuelAnnualCost + " real not null, "
                + KEY_carbonTailPipe + " real not null, "
                + KEY_name + " text not null, "
                + KEY_HighWayDistance + " real not null, "
                + KEY_CityDistance + " real not null, "
                + KEY_OtherDistance + " real not null, "
                + KEY_mode + " integer not null, "
                + KEY_JOURNEY_NAME + " text not null"

                    // Rest  of creation:
                    + ");";
    // Create Utilities Table (Bills) Statement
    private static final String CREATE_TABLE_UTILITY =
        "create table " + TABLE_JOURNEY
            + " (" + KEY_ROWID + " integer primary key autoincrement, "
            // TODO: Place your fields here!
            // + KEY_{...} + " {type} not null"
            //	- Key is the column name you created above.
            //	- {type} is one of: text, integer, real, blob
            //		(http://www.sqlite.org/datatype3.html)
            //  - "not null" means it is a required field (must be given a value).
            // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
            // TODO: Place your fields here!
            + KEY_NAME + " text not null, "
            + KEY_STUDENTNUM + " integer not null, "
            + KEY_FAVCOLOUR + " string not null"
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

    // Add a new set of values to the database.
    public long insertRow(String DB_TABLE, String name, int studentNum, String favColour) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_STUDENTNUM, studentNum);
        initialValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.insert(DB_TABLE, null, initialValues);
    }

    // Add a new set of values to the database.
    public long insertRow(String DB_TABLE, Journey j) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data
        ContentValues initialValues = new ContentValues();
        // Journey
        initialValues.put(KEY_JOURNEY_NAME, j.getName());
        // Car
        Car c = j.getCar();
        initialValues.put(KEY_CAR_NAME, c.getName() );
        initialValues.put(KEY_transDescription, c.getTransDescription());
        initialValues.put(KEY_make, c.getMake());
        initialValues.put(KEY_model, c.getModel());
        initialValues.put(KEY_nickName, c.getNickName());
        initialValues.put(KEY_year, c.getYear());
        initialValues.put(KEY_cityMPG, c.getCityMPG());
        initialValues.put(KEY_highwayMPG, c.getHighwayMPG());
        initialValues.put(KEY_engineDescription, c.getEngineDescription());
        initialValues.put(KEY_engineDispLitres, c.getEngineDispLitres());
        initialValues.put(KEY_fuelType, c.getFuelType());
        initialValues.put(KEY_fuelAnnualCost, c.getFuelAnnualCost());
        initialValues.put(KEY_carbonTailPipe, c.getCarbonTailPipe());
        // Route
        Route r = j.getRoute();
        initialValues.put(KEY_name, r.getName());
        initialValues.put(KEY_HighWayDistance, r.getHighWayDistance());
        initialValues.put(KEY_CityDistance, r.getCityDistance());
        initialValues.put(KEY_OtherDistance, r.getOtherDistance());
        initialValues.put(KEY_mode, r.getMode());

        Log.i(TAG, "insertRow: " + j.toString());
        // Insert it into the database.
        return db.insert(DB_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(String DB_TABLE,long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DB_TABLE, where, null) != 0;
    }

    public void deleteAll(String DB_TABLE) {
        Cursor c = getAllRows(DB_TABLE);
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(DB_TABLE, c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows(String DB_TABLE) {
        String where = null;
        Cursor c = 	db.query(true, DB_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId, String DB_TABLE) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DB_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(String DB_TABLE,long rowId, String name, int studentNum, String favColour) {
        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_STUDENTNUM, studentNum);
        newValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.update(DB_TABLE, newValues, where, null) != 0;
    }



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
            _db.execSQL(CREATE_TABLE_JOURNEY);
            _db.execSQL(CREATE_TABLE_UTILITY);

        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNEY);
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILITY);

            // Recreate new database:
            onCreate(_db);
        }
    }


}