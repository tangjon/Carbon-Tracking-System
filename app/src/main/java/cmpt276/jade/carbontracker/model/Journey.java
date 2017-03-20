package cmpt276.jade.carbontracker.model;

import java.io.Serializable;

/**
 * Created by Sean on 02/03/2017.
 * Journey object stores both the car and route objects used
 *
 */

public class Journey implements Serializable{


    public static String KEY = "JOURNEY";
    private String name;
    private Transportation transType;
    private Route route;
    private Car car;

    //TODO Add getter and setter when these classes are finished
    //private Walk walk;
    //private Transit transit;

    private double totalDriven;
    private double totalEmissionsCity;
    private double totalEmissionsHighway;
    private double totalTravelledEmissions;
    private int mode = 0;  //0 is add.    1 is edit.
    private int position = -1;
    private String Date = "TEMP";

    public Journey(String inputName,Transportation inputTransType , Route inputRoute){
        this.name = inputName;
        this.transType = inputTransType;
        this.route = inputRoute;
        this.car = transType.getCar();
        //TODO See above and will have to change emissions data
        //this.walk = transType.getWalk();
       // this.transit = transType.getTransit;
        if(transType.getCar() != null) {
            this.totalEmissionsCity = calcTotal(mtoKM(transType.getCar().getCityMPG()), route.getCityDistance());
            this.totalEmissionsHighway = calcTotal(mtoKM(transType.getCar().getHighwayMPG()), route.getHighWayDistance());
            this.totalTravelledEmissions = totalEmissionsCity + totalEmissionsHighway;
            this.totalDriven = route.getCityDistance() + route.getHighWayDistance();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Transportation getTransType() {
        return transType;
    }

    public void setTransType(Transportation transType) {
        this.transType = transType;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route input){
        this.route = input;
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

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getTotalEmissionsCity() {
        return totalEmissionsCity;
    }

    public void setTotalEmissionsCity(double totalEmissionsCity) {
        this.totalEmissionsCity = totalEmissionsCity;
    }

    public double getTotalEmissionsHighway() {
        return totalEmissionsHighway;
    }

    public void setTotalEmissionsHighway(double totalEmissionsHighway) {
        this.totalEmissionsHighway = totalEmissionsHighway;
    }

    public double getTotalTravelledEmissions() {
        return totalTravelledEmissions;
    }

    public void setTotalTravelledEmissions(double totalTravelledEmissions) {
        this.totalTravelledEmissions = totalTravelledEmissions;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    //Miles to KM
    public double mtoKM(double input){
        return input * 1.60934;
    }

    //Calculates total emissions
    public double calcTotal(double carKMPG, double routeDistance){
        double total = 0;
        if(car.getFuelType() != null) {
            if (car.getFuelType().equals("Regular Gasoline") || car.getFuelType().equals("Premium Gasoline")) {
                total = 8.89 * (routeDistance / carKMPG);
            }
            if (car.getFuelType().equals("Diesel")) {
                total = 10.16 * (routeDistance / carKMPG);
            }
        }
            return total;

    }

    public Car getCar(){
        return car;
    }


    public double getTotalDriven() {
        return totalDriven;
    }

    public void setTotalDriven(double totalDriven) {
        this.totalDriven = totalDriven;
    }



}
