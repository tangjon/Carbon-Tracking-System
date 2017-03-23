package cmpt276.jade.carbontracker.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import cmpt276.jade.carbontracker.enums.Transport;

/**
 * Created by Sean on 02/03/2017.
 * Journey object stores both the car and route objects used
 *
 */

public class Journey implements Serializable{


    public static final double GASOLINECO2FACTOR = 8.89;
    public static final double DIESELCO2FACTOR = 10.16;
    public static final double KILOMETERSINAMILE = 1.60934;
    public static final double TRANSLINKBUSEMISSIONSTAT = 1.7; //got this number from translink
    public static final double BOMBARDIERMARK2EMISSIONSTAT = 3.10; // kWh/km from a very long PDF hidden away on the internet
    private final double CALC_ELEC = 0.009;     // 9000Kg CO2 / GWh
    public static String KEY = "JOURNEY";


    private String name;
    private Transportation transType;
    private Route route;
    private double totalDriven;
    private double totalEmissionsCity;
    private double totalEmissionsHighway;
    private double totalTravelledEmissions;
    private int mode = 0;  //0 is add.    1 is edit.
    private int position;
    private String Date;
    private Date dateObj;
    private double busEmissions;
    private double skytrainEmissions;

    public Journey(String inputName,Transportation inputTransType , Route inputRoute){
        this.name = inputName;
        this.transType = inputTransType;
        this.route = inputRoute;
        if(transType.getCar() != null) {
            this.totalEmissionsCity = calcTotal(mtoKM(transType.getCar().getCityMPG()), route.getCityDistance());
            this.totalEmissionsHighway = calcTotal(mtoKM(transType.getCar().getHighwayMPG()), route.getHighWayDistance());
            this.totalTravelledEmissions = totalEmissionsCity + totalEmissionsHighway;
            this.totalDriven = route.getCityDistance() + route.getHighWayDistance();
        }
        else if(transType.getBus() != null) {
            this.totalDriven = route.getOtherDistance();
            this.busEmissions = totalDriven* TRANSLINKBUSEMISSIONSTAT;

        }
        else if(transType.getSkytrain()!= null){
            this.totalDriven = route.getOtherDistance();
            this.skytrainEmissions= (totalDriven * BOMBARDIERMARK2EMISSIONSTAT) * CALC_ELEC ;
        }
        else{
            this.totalDriven = route.getOtherDistance();
            this.totalTravelledEmissions = 0;
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
        String[] dateCheck = date.trim().split("/", 3);
        int month = 0;
        int day = 0;
        int year = 0;

        day = Integer.parseInt(dateCheck[0]);
        month = Integer.parseInt(dateCheck[1]) - 1;
        year = Integer.parseInt(dateCheck[2]);

        Log.i("spinner", "year = "+year+" month = "+month+" day = "+day);

        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        setDateObj(c.getTime());

        Log.i("spinner", "dateObj = "+dateObj.toString());
    }

    //Miles to KM
    public double mtoKM(double input){
        return input * KILOMETERSINAMILE;
    }

    //Calculates total emissions
    public double calcTotal(double carKMPG, double routeDistance){
        double total = 0;
        if(transType.getCar().getFuelType() != null) {
            if (transType.getCar().getFuelType().equals("Regular Gasoline") || transType.getCar().getFuelType().equals("Premium Gasoline") || transType.getCar().getFuelType().equals("Midgrade")) {
                total = GASOLINECO2FACTOR * (routeDistance / carKMPG);
            }
            if (transType.getCar().getFuelType().equals("Diesel")) {
                total = DIESELCO2FACTOR * (routeDistance / carKMPG);
            }
        }
        return total;

    }


    public double getTotalDriven() {
        return totalDriven;
    }

    public void setTotalDriven(double totalDriven) {
        this.totalDriven = totalDriven;
    }


    public double getBusEmissions() {
        return busEmissions;
    }

    public void setBusEmissions(double busEmissions) {
        this.busEmissions = busEmissions;
    }

    public double getSkytrainEmissions() {
        return skytrainEmissions;
    }

    public void setSkytrainEmissions(double skytrainEmissions) {
        this.skytrainEmissions = skytrainEmissions;
    }



    @Override
    public String toString() {
        return "Journey{" +
                "name='" + name + '\'' +
                ", transType=" + transType +
                ", route=" + route +
                ", Date='" + Date + '\'' +
                ", totalDriven=" + totalDriven +
                ", totalEmissionsCity=" + totalEmissionsCity +
                ", totalEmissionsHighway=" + totalEmissionsHighway +
                ", totalTravelledEmissions=" + totalTravelledEmissions +
                ", mode=" + mode +
                ", position=" + position +
                '}';
    }

    public Date getDateObj() {
        return dateObj;
    }

    public void setDateObj(Date dateObj) {
        this.dateObj = dateObj;
    }
}
