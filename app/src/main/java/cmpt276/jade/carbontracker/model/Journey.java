package cmpt276.jade.carbontracker.model;

/**
 * Created by Sean on 02/03/2017.
 */

public class Journey {


    private Car car;
    private Route route;
    private double totalCity;
    private double totalHighway;

    public Journey(Car inputCar , Route inputRoute){
        this.car = inputCar;
        this.route = inputRoute;
        this.totalCity = calcTotalCity(car.getuCity(),route.getCityDriving());
        this.totalHighway = calcTotalHway(car.getuHighway(), route.getHighwayDriving());
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car input) {
        this.car = input;
    }

    public Route getRoute() {
        return route;
    }

    public void setCar(Route input) {
        this.route = input;
    }

    public double getTotalHighway() {
        return totalHighway;
    }

    public void setTotalHighway(double totalHighway) {
        this.totalHighway = totalHighway;
    }

    public double getTotalCity() {
        return totalCity;
    }

    public void setTotalCity(double totalCity) {
        this.totalCity = totalCity;
    }

    public double calcTotalCity(double carCity, double routeCity){
        double total= carCity * routeCity;
        return total;
    }
    public double calcTotalHway(double carHway, double routeHway){
        double total= carHway * routeHway;
        return total;
    }

}
