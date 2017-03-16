package cmpt276.jade.carbontracker.model;

import android.util.Log;

import java.io.Serializable;

//Route class for walk,bike,bus,skytrain   Route(Nickname,Distance)
//has getter and setter
public class OtherRoute implements Serializable {
    private String name;
    private double Distance;
    private int mode;

    public OtherRoute(String name, double Distance,int mode) {
        this.name = name;
        this.Distance = Distance;
        this.mode=mode;
        //2 for bike and walk,3 for bus, 4 for skytrain
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(int Distance) {
        if (Distance < 0) {
            throw new IllegalArgumentException();
        } else {
            this.Distance = Distance;
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
