package cmpt276.jade.carbontracker.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Singleton facade for use by UI
 */

public class Emission {
    private static final Emission instance = new Emission();
    private final String SPREF_KEY = "cmpt276.jade.carbontracker";
    private final String KEY_CAR_COLLECTION = "CarCollection";
    private CarCollection carCollection = new CarCollection();
    private JourneyCollection journeyCollection = new JourneyCollection();
    private Utilities utilities = new Utilities();


    private Settings settings= new Settings();

    /**
     * useful for keeping temporary journey to work with throughout journey creation process
     * without using SharedPreferences/Intents
     */
    private Journey buffer;
    private Bill bufferBill;

    public static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public static double round(double d) {
        DecimalFormat format = new DecimalFormat("#.##");
        return Double.valueOf(format.format(d));
    }

    private Emission() {
        if (instance != null)
            throw new IllegalStateException("Emission singleton already instantiated");

    }

    public static Emission getInstance() {
        return instance;
    }

    public CarCollection getCarCollection() {
        return carCollection;
    }

    public void setCarCollection(CarCollection cc) {
        carCollection = cc;
    }

    public JourneyCollection getJourneyCollection() {
        return journeyCollection;
    }

    public void setJourneyCollection(JourneyCollection jc) {
        journeyCollection = jc;
    }

    public Journey getJourneyBuffer() {
        return buffer;
    }

    public void setJourneyBuffer(Journey j) {
        buffer = j;
    }

    public void saveCarCollection(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SPREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        editor.putString(KEY_CAR_COLLECTION, gson.toJson(carCollection));

        editor.apply();
    }

    public void loadCarCollection(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SPREF_KEY, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_CAR_COLLECTION, null);
        carCollection = gson.fromJson(json, carCollection.getClass());

        if (carCollection == null) carCollection = new CarCollection();
    }

    public Utilities getUtilities() {
        return utilities;
    }

    public void setUtilities(Utilities utilities) {
        this.utilities = utilities;
    }

    public Bill getBufferBill() {
        return bufferBill;
    }

    public void setBufferBill(Bill bufferBill) {
        this.bufferBill = bufferBill;
    }


    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

}
