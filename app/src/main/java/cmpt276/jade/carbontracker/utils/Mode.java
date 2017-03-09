package cmpt276.jade.carbontracker.utils;

/**
 * Use to distinguish Add/Edit mode to decide which interface is shown
 */

public enum Mode {
    ADD("Add"), EDIT("Edit");

    private String mode;

    private Mode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
