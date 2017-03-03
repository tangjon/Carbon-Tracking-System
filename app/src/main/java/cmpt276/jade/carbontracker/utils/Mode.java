package cmpt276.jade.carbontracker.utils;

/**
 * Created by tangj on 3/3/2017.
 */

public enum Mode {
    ADD("Add"), EDIT("Edit"), DELETE("Delete");

    private String mode;

    private Mode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
