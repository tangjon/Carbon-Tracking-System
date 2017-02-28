package cmpt276.jade.carbontracker.model;

/**
 * Created by tangj on 2/27/2017.
 */

public class Car {
    private String make; // Manufacturer
    private String model;
    private String nickname;
    private int year;
    private int uCity;
    private int uHighway;
    private int carbonTailPipe;

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

    public int getuCity() {
        return uCity;
    }

    public void setuCity(int uCity) {
        this.uCity = uCity;
    }

    public int getuHighway() {
        return uHighway;
    }

    public void setuHighway(int uHighway) {
        this.uHighway = uHighway;
    }

    public int getCarbonTailPipe() {
        return carbonTailPipe;
    }

    public void setCarbonTailPipe(int carbonTailPipe) {
        this.carbonTailPipe = carbonTailPipe;
    }
}
