package cmpt276.jade.carbontracker.model;

/**
 * Created by tangj on 2/27/2017.
 */

public class Car {
    private String make; // Manufacturer
    private String model;
    private String nickname;
    private int year;
    private double uCity;
    private double uHighway;
    private double carbonTailPipe;

    public Car(String nickname, String make, String model, int year, double uCity, double uHighway, double carbonTailPipe) {
        this.make = make;
        this.model = model;
        this.nickname = nickname;
        this.year = year;
        this.uCity = uCity;
        this.uHighway = uHighway;
        this.carbonTailPipe = carbonTailPipe;
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
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getuCity() {
        return uCity;
    }

    public void setuCity(int uCity) {
        this.uCity = uCity;
    }

    public double getuHighway() {
        return uHighway;
    }

    public void setuHighway(int uHighway) {
        this.uHighway = uHighway;
    }

    public double getCarbonTailPipe() {
        return carbonTailPipe;
    }

    public void setCarbonTailPipe(int carbonTailPipe) {
        this.carbonTailPipe = carbonTailPipe;
    }

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", nickname='" + nickname + '\'' +
                ", year=" + year +
                ", uCity=" + uCity +
                ", uHighway=" + uHighway +
                ", carbonTailPipe=" + carbonTailPipe +
                '}';
    }
}
