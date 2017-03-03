package cmpt276.jade.carbontracker.model;

import java.io.Serializable;

/**
 * Created by Sean on 02/03/2017.
 */

public class Route implements Serializable{

    private double cityDriving = 5;
    private double highwayDriving = 10.2;
    private String nickName = "Route name";




    public double getCityDriving() {
        return cityDriving;
    }

    public void setCityDriving(int cityDriving) {
        this.cityDriving = cityDriving;
    }

    public double getHighwayDriving() {
        return highwayDriving;
    }

    public void setHighwayDriving(int highwayDriving) {
        this.highwayDriving = highwayDriving;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double totalTravelled(){
        return cityDriving+highwayDriving;
    }
}
