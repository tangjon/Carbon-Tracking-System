package cmpt276.jade.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

public class RouteCollection {

    private List<Route> List_Rout = new ArrayList<>();

    public int getTotleHighWayDistance() {
        int HighWayDistance = 0;
        String[] list = new String[countRoutes()];
        for (int i = 0; i < countRoutes(); i++) {
            Route route = getRouteByIndex(i);
            HighWayDistance = HighWayDistance + route.getHighWayDistance();
        }
        return HighWayDistance;
    }

    public int getTotleCityDistance() {
        int CityDistance = 0;
        String[] list = new String[countRoutes()];
        for (int i = 0; i < countRoutes(); i++) {
            Route route = getRouteByIndex(i);
            CityDistance = CityDistance + route.getHighWayDistance();
        }
        return CityDistance;
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
            detail[i] = "Route: " + route.getName() + ", HighWay:"
                    + route.getHighWayDistance() + "km, " +
                    " City:" + route.getCityDistance() + "km.";

            //***********VERSON of hide the route instead of deleting it*********//
            //***********Need to test in Monday meeting**************************//
            /*
            Route route = getRouteByIndex(i);
            if(route.getSelection()=="NO")
            {
            detail[i] = ""+ route.getName() + ",           "
                    + route.getHighWayDistance() + "km, "+
                    "         "+route.getCityDistance()+ "km.";
            }
            else
            {
                detail[i] = "You deleted Roure:"+route.getName()
                +
                "   Long Press to edit.";
            }*/

        }
        return detail;
    }

    private void validateIndexWithException(int index) {
        if (index < 0 || index >= countRoutes()) {
            throw new IllegalArgumentException();
        }
    }
}
