package cmpt276.jade.carbontracker.model;

/**
 * Singleton facade for use by UI
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
}
