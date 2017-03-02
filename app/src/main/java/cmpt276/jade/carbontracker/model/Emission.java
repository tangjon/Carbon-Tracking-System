package cmpt276.jade.carbontracker.model;

/**
 * Singleton facade for use by UI
 */

public class Emission {
    private static Emission emission = new Emission();
    private CarCollection carCollection = new CarCollection();
    // TODO: give access to other collections and functions

    public static Emission getInstance() {
        return emission;
    }

    private Emission() {
        emission = new Emission();
    }

    public CarCollection getCarCollection() {
        return carCollection;
    }
}
