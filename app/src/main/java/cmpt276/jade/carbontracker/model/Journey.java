package cmpt276.jade.carbontracker.model;

import java.io.Serializable;

/**
 * Created by Sean on 02/03/2017.
 */

public class Journey implements Serializable{



    private String name;
    private Car car;
    private Route route;
    private double totalDriven;
    private double totalEmissionsCity;
    private double totalEmissionsHighway;
    private double totalTravelledEmissions;
    private int mode = 0;  //0 is add.    1 is edit.
    private int position = -1;
    private String Date = "TEMP";

    public Journey(String inputName,Car inputCar , Route inputRoute){
        this.name = inputName;
        this.car = inputCar;
        this.route = inputRoute;
        this.totalEmissionsCity = calcTotalCity(mtoKM(car.getCityMPG()), route.getCityDistance());
        this.totalEmissionsHighway = calcTotalHway(mtoKM(car.getHighwayMPG()), route.getHighWayDistance());
        this.totalTravelledEmissions = totalEmissionsCity+totalEmissionsHighway;
        this.totalDriven = route.getCityDistance() + route.getHighWayDistance();
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

    public void setCar(Car input) {
        this.car = input;
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

    //Calculates total City driving
    public double calcTotalCity(double carCity, double routeCity){
        double total = 0;
        if (car.getFuelType() == "gasoline") {
            total = 3.78541 * (8.89 * (routeCity / carCity));

        }
        else if(car.getFuelType() == "diesel"){
            total = 3.78541 * (10.16 * (routeCity / carCity));
        }
        return total;
    }
    //Calculates total Highway driving
    public double calcTotalHway(double carHway, double routeHway){
        double total = 0;
        if (car.getFuelType() == "gasoline") {
            total = 3.78541 * (8.89 * (routeHway / carHway));
        }
        if (car.getFuelType() == "diesel") {
            total = 3.78541 * (10.16 * (routeHway / carHway));
        }
        return total;
    }


    public double getTotalDriven() {
        return totalDriven;
    }

    public void setTotalDriven(double totalDriven) {
        this.totalDriven = totalDriven;
    }



}
