package cmpt276.jade.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * RouteCollection has a list that contain routes,
 * Can add or remove routes by index
 */
public class RouteCollection {
    private String nickname;

    private List<Route> List_Rout = new ArrayList<>();

    public double getTotleHighWayDistance() {
        double HighWayDistance = 0;
        for (int i = 0; i < countRoutes(); i++) {
            Route route = getRouteByIndex(i);
            HighWayDistance = HighWayDistance + route.getHighWayDistance();
        }
        return HighWayDistance;
    }

    public void DeleteAll() {
        List_Rout.clear();
    }

    public double getTotleCityDistance() {
        double CityDistance = 0;
        for (int i = 0; i < countRoutes(); i++) {
            Route route = getRouteByIndex(i);
            CityDistance = CityDistance + route.getCityDistance();
        }
        return CityDistance;
    }

    public void SetJourneyName(String name) {
        this.nickname = name;
    }

    public String getJourneyName() {
        return this.nickname;
    }

    public void addRoute(Route route) {
        List_Rout.add(route);
    }


    public void changeRoute(Route route, int index) {
        validateIndexWithException(index);
        List_Rout.remove(index);
        List_Rout.add(index, route);
    }

    public void deleteRoute(int index) {
        validateIndexWithException(index);
        List_Rout.remove(index);
    }

    public int countRoutes() {
        return List_Rout.size();
    }

    public Route getRouteByIndex(int index) {
        validateIndexWithException(index);
        return List_Rout.get(index);
    }

    public String[] Detail() {
        String[] detail = new String[countRoutes()];
        for (int i = 0; i < countRoutes(); i++) {
            Route route = getRouteByIndex(i);
            int mode = route.getMode();
            if (mode == 2)//bike
            {
                detail[i] = "Bike: " + route.getName()
                        + ", Distance:"
                        + route.getOtherDistance() + "km.";
            } else if (mode == 3)//bus mode
            {
                detail[i] = "Bus: " + route.getName() + ", Distance:"
                        + route.getOtherDistance() + "km.";
            } else if (mode == 4)//skytrain mode
            {
                detail[i] = "Sktrain: " + route.getName() + ", Distance:"
                        + route.getOtherDistance() + "km.";
            } else if (mode == 5)//walk mode
            {
                detail[i] = "Walk: " + route.getName() + ", Distance:"
                        + route.getOtherDistance() + "km.";
            } else {
                detail[i] = "Route: " + route.getName() + ", HighWay:"
                        + route.getHighWayDistance() + "km, " +
                        " City:" + route.getCityDistance() + "km.";
            }
        }
        return detail;
    }

    private void validateIndexWithException(int index) {
        if (index < 0 || index >= countRoutes()) {
            throw new IllegalArgumentException();
        }
    }
}
