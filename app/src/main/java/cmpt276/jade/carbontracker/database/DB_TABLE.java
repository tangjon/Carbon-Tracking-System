package cmpt276.jade.carbontracker.database;

/**
 * Created by tangj on 3/20/2017.
 */

public enum DB_TABLE {
    TABLE_CAR("cars"), TABLE_ROUTE("routes"), TABLE_JOURNEY("journeys");

    private String name;

    private DB_TABLE(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
