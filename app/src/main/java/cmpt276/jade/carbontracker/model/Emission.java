package cmpt276.jade.carbontracker.model;

/**
 * Singleton facade for use by UI
 * todo: verify syntax
 *
 * Usage:   obtain references through getters, once activity is done with data, use setters to save
 *          changes made to collections (i think)
 */

public class Emission {
    private static Emission emission = new Emission();
    private CarCollection carCollection;
    private RouteCollection routeCollection;
    private JourneyCollection journeyCollection;

    public static Emission getInstance() {
        return emission;
    }

    private Emission() {
        carCollection = new CarCollection();
        routeCollection = new RouteCollection();
        journeyCollection = new JourneyCollection();
    }

    public CarCollection getCarCollection() {
        return carCollection;
    }

    public RouteCollection getRouteCollection() {
        return routeCollection;
    }

    public JourneyCollection getJourneyCollection() {
        return journeyCollection;
    }

    public void setCarCollection(CarCollection cc) {
        carCollection = cc;
    }

    public RouteCollection setRouteCollection(RouteCollection rc) {
        routeCollection = rc;
    }

    public JourneyCollection setJourneyCollection(JourneyCollection jc) {
        journeyCollection = jc;
    }
}
