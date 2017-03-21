package cmpt276.jade.carbontracker.model;

import java.io.Serializable;

import cmpt276.jade.carbontracker.enums.Transport;

/**
 * Created by Sean on 12/03/2017.
 */

public class Transportation implements Serializable {

    Transport transMode;

    public void setTransMode(Transport transMode) {
        this.transMode = transMode;
    }

    public Transport getTransMode() {
        return transMode;
    }

    private Car car;
 //   private Transit transit;
 //   private Walk walk;


    public Transportation(){

    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
/*
    public Transit getTransit() {
        return transit;
    }

    public void setTransit(Transit transit) {
        this.transit = transit;
    }

    public Walk getWalk() {
        return walk;
    }

    public void setWalk(Walk walk) {
        this.walk = walk;
    }
  */

}
