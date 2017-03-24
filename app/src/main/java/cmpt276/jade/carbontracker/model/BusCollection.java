package cmpt276.jade.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 20/03/2017.
 * The list of bus objects
 */

public class BusCollection {

    private List<Bus> busList = new ArrayList<>();

    //add bus to list
    public void addBus(Bus bus) {
        busList.add(bus);
    }

    //Deletes bus from list
    public void deleteBus(int index) {
        busList.remove(index);
    }

    //edits bus
    public void editBus(Bus bus, int index) {
        busList.remove(index);
        busList.add(index, bus);
    }

    //get Bus from list
    public Bus getBus(int index) {
        return busList.get(index);
    }

    //counts number of objects in list
    public int countBuses() {
        return busList.size();
    }

    public List<Bus> getBusList() {
        return busList;
    }

    public String[] getBusDetails() {
        String[] details = new String[countBuses()];
        for (int i = 0; i < countBuses(); i++) {
            Bus bus = getBus(i);
            details[i] = bus.getNickName() + " - " + bus.getRouteNumber();
        }
        return details;

    }

}
