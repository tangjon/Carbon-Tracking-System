package cmpt276.jade.carbontracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Utilities implements Serializable {
    private int numResidents;
    private List<Object> listBillElec;  // change <Object> to correct utility object when possible
    private List<Object> listBillGas;

    Utilities() {
        loadData();
    }

    private void loadData() {
        numResidents = 0;
        listBillElec = new ArrayList<>();
        listBillGas = new ArrayList<>();

    }

    public int getNumResidents() {
        return numResidents;
    }

    public void setNumResidents(int numResidents) {
        if (numResidents > 0) this.numResidents = numResidents;
    }

    public List<Object> getListBillElec() {
        return listBillElec;
    }

    public void setListBillElec(List<Object> listBillElec) {
        this.listBillElec = listBillElec;
    }

    public List<Object> getListBillGas() {
        return listBillGas;
    }

    public void setListBillGas(List<Object> listBillGas) {
        this.listBillGas = listBillGas;
    }
}
