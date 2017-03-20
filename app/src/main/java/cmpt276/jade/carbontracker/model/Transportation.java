package cmpt276.jade.carbontracker.model;

import java.io.Serializable;

/**
 * Created by Sean on 12/03/2017.
 */

public class Transportation implements Serializable {


    private Car car;
    private Bus bus;
    private Skytrain train;
  //  private Walk walk;


    public Transportation(){

    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Skytrain getSkytrain() {
        return train;
    }

    public void setSkytrain(Skytrain skytrain) {
        this.train = skytrain;
    }


}
