package cmpt276.jade.carbontracker.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Car model stores information on a single car
 * Each car model has a unique key
 */

public class Car implements Serializable{
    private String transDescription;
    private String make; // Manufacturer
    private String model;
    private String nickName;
    private int year;
    private int cityMPG;
    private int highwayMPG;
    private String engineDescription;
    private double engineDispLitres;
    private String fuelType;
    private int fuelAnnualCost;
    private double carbonTailPipe;
    private UUID KEY;

    public Car(){
        this.KEY = UUID.randomUUID();
    }


    // Constructor with nickname
    public Car(String nickName, String make, String model, int year, int cityMPG, int highwayMPG, String engineDescription,
               double engineDispLitres, String fuelType, int fuelAnnualCost, double carbonTailPipe, String transDescription) {
        this.nickName = nickName;
        this.make = make;
        this.model = model;
        this.year = year;
        this.cityMPG = cityMPG;
        this.highwayMPG = highwayMPG;
        this.engineDescription = engineDescription;
        this.engineDispLitres = engineDispLitres;
        this.fuelType = fuelType;
        this.fuelAnnualCost = fuelAnnualCost;
        this.carbonTailPipe = carbonTailPipe;
        this.KEY = UUID.randomUUID();
        this.transDescription = transDescription;
    }

    public Car(String make, String model, int year, int cityMPG, int highwayMPG, String engineDescription,
               double engineDispLitres, String fuelType, int fuelAnnualCost, double carbonTailPipe, String transDescription) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.cityMPG = cityMPG;
        this.highwayMPG = highwayMPG;
        this.engineDescription = engineDescription;
        this.engineDispLitres = engineDispLitres;
        this.fuelType = fuelType;
        this.fuelAnnualCost = fuelAnnualCost;
        this.carbonTailPipe = carbonTailPipe;
        this.KEY = UUID.randomUUID();
        this.transDescription = transDescription;
    }

    public String getName(){
        return "" + getMake() + " : " + getModel() + " : " + getYear();
    }

    public String getTransDescription() {
        return transDescription;
    }

    public void setTransDescription(String transDescription) {
        this.transDescription = transDescription;
    }

    public UUID getKEY() {
        return KEY;
    }

    public void setKEY(UUID KEY) {
        this.KEY = KEY;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNickname() {
        return nickName;
    }

    public void setNickname(String nickname) {
        this.nickName = nickname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCityMPG() {
        return cityMPG;
    }

    public void setCityMPG(int cityMPG) {
        this.cityMPG = cityMPG;
    }

    public int getHighwayMPG() {
        return highwayMPG;
    }

    public void setHighwayMPG(int highwayMPG) {
        this.highwayMPG = highwayMPG;
    }

    public String getEngineDescription() {
        return engineDescription;
    }

    public void setEngineDescription(String engineDescription) {
        this.engineDescription = engineDescription;
    }

    public double getEngineDispLitres() {
        return engineDispLitres;
    }

    public void setEngineDispLitres(double engineDispLitres) {
        this.engineDispLitres = engineDispLitres;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getFuelAnnualCost() {
        return fuelAnnualCost;
    }

    public void setFuelAnnualCost(int fuelAnnualCost) {
        this.fuelAnnualCost = fuelAnnualCost;
    }

    public void setCarbonTailPipe(double carbonTailPipe) {
        this.carbonTailPipe = carbonTailPipe;
    }

    public double getCarbonTailPipe() {
        return carbonTailPipe;
    }

    public void setCarbonTailPipe(int carbonTailPipe) {
        this.carbonTailPipe = carbonTailPipe;
    }

    public Car copy(){
        return new Car(nickName,  make,  model,  year,  cityMPG,  highwayMPG,  engineDescription,
         engineDispLitres,  fuelType,  fuelAnnualCost,  carbonTailPipe,  transDescription);
    }

    public String toStringNoKey() {
        return "Car{" +
                "nickName='" + nickName + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", transDescription='" + transDescription + '\'' +
                ", year=" + year +
                ", cityMPG=" + cityMPG +
                ", highwayMPG=" + highwayMPG +
                ", engineDescription='" + engineDescription + '\'' +
                ", engineDispLitres=" + engineDispLitres +
                ", fuelType='" + fuelType + '\'' +
                ", fuelAnnualCost=" + fuelAnnualCost +
                ", carbonTailPipe=" + carbonTailPipe +
                '}';
    }


    @Override
    public String toString() {
        return "Car{" +
                "nickName='" + nickName + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", transDescription='" + transDescription + '\'' +
                ", year=" + year +
                ", cityMPG=" + cityMPG +
                ", highwayMPG=" + highwayMPG +
                ", engineDescription='" + engineDescription + '\'' +
                ", engineDispLitres=" + engineDispLitres +
                ", fuelType='" + fuelType + '\'' +
                ", fuelAnnualCost=" + fuelAnnualCost +
                ", carbonTailPipe=" + carbonTailPipe +
                ", KEY=" + KEY +
                '}';
    }
}
