package cmpt276.jade.carbontracker.model;

import java.io.Serializable;

/**
 * Created by Sean on 12/03/2017.
 */

public class Transportation implements Serializable {


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
