package cmpt276.jade.carbontracker.enums;

/**
 * enum: Transport
 * Description: Identification for different types of Transportation are available
 *
 * Bugs:
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
