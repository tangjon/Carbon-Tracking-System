package cmpt276.jade.carbontracker.model;

import android.util.Log;

import java.io.Serializable;

//Route class   Route(Nickname,highway Distance,city Distance)
//has getter and setter
public class Route implements Serializable {
    private String name;
    private double HighWayDistance;
    private double CityDistance;
    private double OtherDistance;//for bike,walk,bus,skytrain
    private int mode;//2 for bike and walk,3 for bus, 4 for skytrain

    public Route(String name, double HighWayDistance, double CityDistance) {
        this.name = name;
        this.HighWayDistance = HighWayDistance;
        Log.i("HELLO", "Route: " + HighWayDistance);
        this.CityDistance = CityDistance;
        Log.i("HELLO", "Route: " + CityDistance);
    }

    public double getOtherDistance() {
        return OtherDistance;
    }

    public void setOtherDistance(double OtherDistance) {
        this.OtherDistance = OtherDistance;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public double getHighWayDistance() {
        return HighWayDistance;
    }

    public void setHighWayDistance(double HighWayDistance) {
        if (HighWayDistance < 0) {
            throw new IllegalArgumentException();
        } else {
            this.HighWayDistance = HighWayDistance;
        }
    }

    public double getCityDistance() {
        return CityDistance;
    }

    public void setCityDistance(int CityDistance) {
        if (CityDistance < -2) {
            throw new IllegalArgumentException();
        } else {
            this.CityDistance = CityDistance;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException();
        } else {
            this.name = name;
        }
    }
}
