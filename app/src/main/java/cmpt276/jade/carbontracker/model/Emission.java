package cmpt276.jade.carbontracker.model;

/**
 * Singleton facade for use by UI
 * todo: verify syntax
 *
 * Usage:   obtain references through getters; once activity is done with data, use setters to save
 *          changes made to collections
 */

public class Emission {
    private static final Emission instance = new Emission();
    private CarCollection carCollection = new CarCollection();
    // private RouteCollection routeCollection = new RouteCollection();
    // private JourneyCollection journeyCollection = new JourneyCollection();

    public static Emission getInstance() {
        return instance;
    }

    private Emission() {
        if (instance != null)
            throw new IllegalStateException("Emission singleton already instantiated");
        // carCollection = new CarCollection();
        // routeCollection = new RouteCollection();
        // journeyCollection = new JourneyCollection();
    }

    public CarCollection getCarCollection() {
        return carCollection;
    }

    /*
    public RouteCollection getRouteCollection() {
        return routeCollection;
    }

    public JourneyCollection getJourneyCollection() {
        return journeyCollection;
    }
    */

    public void setCarCollection(CarCollection cc) {
        carCollection = cc;
    }

    /*
    public static RouteCollection setRouteCollection(RouteCollection rc) {
        routeCollection = rc;
    }

    public static JourneyCollection setJourneyCollection(JourneyCollection jc) {
        journeyCollection = jc;
    }
    */
}
