package cmpt276.jade.carbontracker.model;

import java.io.Serializable;

/**
 * Created by Sean on 02/03/2017.
 */

public class Journey implements Serializable{



    private String name;
    private Car car;
    private Route route;
    private double totalEmissionsCity;
    private double totalEmissionsHighway;
    private double totalTravelledEmissions;

    public Journey(String inputName,Car inputCar , Route inputRoute){
        this.name = inputName;
        this.car = inputCar;
        this.route = inputRoute;
        this.totalEmissionsCity = calcTotalCity(car.getCityMPG(), route.getCityDistance());
        this.totalEmissionsHighway = calcTotalHway(car.getHighwayMPG(), route.getHighWayDistance());
        this.totalTravelledEmissions = totalEmissionsCity+totalEmissionsHighway;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Route input) {
        this.route = input;
    }

    public void setCar(Car input) {
        this.car = input;
    }

    public Route getRoute() {
        return route;
    }

    public double getTotalHighway() {
        return totalEmissionsHighway;
    }

    public void setTotalHighway(double totalHighway) {
        this.totalEmissionsHighway = totalHighway;
    }

    public double getTotalCity() {
        return totalEmissionsCity;
    }

    public void setTotalCity(double totalCity) {
        this.totalEmissionsCity = totalCity;
    }

    public double getTotalTravelled() {
        return totalTravelledEmissions;
    }

    public void setTotalTravelled(double totalTravelled) {
        this.totalTravelledEmissions = totalTravelled;
    }

    //Calculates total City driving
    public double calcTotalCity(double carCity, double routeCity){
        double total= carCity * routeCity;
        return total;
    }
    //Calculates total Highway driving
    public double calcTotalHway(double carHway, double routeHway){
        double total= carHway * routeHway;
        return total;
    }


}
