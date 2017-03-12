package cmpt276.jade.carbontracker.model;

public class Utilities {
    private int numResidents;

    Utilities() {
        loadData();
    }

    private void loadData() {
        numResidents = 0;
    }

    public int getNumResidents() {
        return numResidents;
    }

    public void setNumResidents(int numResidents) {
        if (numResidents > 0) this.numResidents = numResidents;
    }
}
