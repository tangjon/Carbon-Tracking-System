package cmpt276.jade.carbontracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cmpt276.jade.carbontracker.utils.BillType;

public class Utilities implements Serializable {
    private int numResidents;
    private List<Bill> listBillElec;
    private List<Bill> listBillGas;
    private Bill buffer;

    Utilities() {
        loadData();
    }

    // TODO: 14/03/17 load previous data if any
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

    public List<Bill> getListBillElec() {
        return listBillElec;
    }

    public void setListBillElec(List<Bill> listBillElec) {
        this.listBillElec = listBillElec;
    }

    public List<Bill> getListBillGas() {
        return listBillGas;
    }

    public void setListBillGas(List<Bill> listBillGas) {
        this.listBillGas = listBillGas;
    }

    public Bill getBuffer() {
        return buffer;
    }

    public void setBuffer(Bill buffer) {
        this.buffer = buffer;
    }

    public void addBill(BillType type, Bill bill) {
        if (type == BillType.ELECTRIC) listBillElec.add(bill);
        else listBillGas.add(bill);
    }

    public void editBill(BillType type, Bill newBill, int index) {
        if (type == BillType.ELECTRIC) {
            listBillElec.remove(index);
            listBillElec.add(index, newBill);
        }
        else {
            listBillGas.remove(index);
            listBillGas.add(index, newBill);
        }
    }

    public void deleteBill(BillType type, int index) {
        if (type == BillType.ELECTRIC) listBillElec.remove(index);
        else listBillGas.remove(index);
    }

}
