package cmpt276.jade.carbontracker.enums;

/**
 * Created by tangj on 3/17/2017.
 */

public enum Transport {
    CAR("Car"), BIKE("Bike"), WALK("Walk"), BUS("Bus"), SKYTRAIN("SkyTrain"), TRANSIT("Transit");

    private String transport;

    private Transport(String transport) {
        this.transport = transport;
    }

    @Override
    public String toString() {
        return transport;
    }
}
