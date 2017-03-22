package cmpt276.jade.carbontracker.model;

import java.io.Serializable;

import cmpt276.jade.carbontracker.enums.Transport;

/**
 * Created by Sean on 12/03/2017.
 */

public class Transportation implements Serializable {

    private Car car;
    private Bus bus;
    private Skytrain train;
    private Transport transMode;


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


    public Transport getTransMode() {
        return transMode;
    }

    public void setTransMode(Transport transMode) {
        this.transMode = transMode;
    }

}
