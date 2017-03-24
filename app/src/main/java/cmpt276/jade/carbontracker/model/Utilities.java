package cmpt276.jade.carbontracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cmpt276.jade.carbontracker.enums.BillType;

/**
 * Stores either a gas bill list or electricity bill list
 */
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
        numResidents = 1;
        listBillElec = new ArrayList<>();
        listBillGas = new ArrayList<>();

    }

    public List<Bill> getBillsOnDay(Date date, BillType type) {
        List<Bill> usableBills = new ArrayList<>();
        List<Bill> bills = new ArrayList<>();

        if (type == BillType.ELECTRIC) bills.addAll(listBillElec);
        else bills.addAll(listBillGas);

        for (Bill b : bills) {
            if (b == null) return usableBills;
            if ((b.getStartDate().before(date) && b.getEndDate().after(date))
                    || b.getStartDate().compareTo(date) == 0
                    || b.getEndDate().compareTo(date) == 0)
                usableBills.add(b);
        }

        return usableBills;
    }

    public List<Bill> getBillsWithinRange(Date start, Date end, BillType type) {
        List<Bill> usableBills = new ArrayList<>();
        List<Bill> bills = new ArrayList<>();

        if (type == BillType.ELECTRIC) bills.addAll(listBillElec);
        else bills.addAll(listBillGas);

        for (Bill b : bills) {
            if (b.getStartDate().after(start) && b.getStartDate().before(end))
                usableBills.add(b);
            else if (b.getEndDate().after(start) && b.getEndDate().before(end))
                usableBills.add(b);
            else if (b.getStartDate().compareTo(start) == 0 || b.getStartDate().compareTo(end) == 0
                    || b.getEndDate().compareTo(start) == 0 || b.getEndDate().compareTo(end) == 0)
                usableBills.add(b);
        }

        return usableBills;
    }

    public Bill getNearestBill(Date date, BillType type) {
        List<Bill> bills;

        if (type == BillType.ELECTRIC) bills = listBillElec;
        else bills = listBillGas;

        long dateDiffStart, dateDiffEnd, dateDiffStartLast, dateDiffEndLast;
        dateDiffStartLast = 0;
        dateDiffEndLast = 0;
        Bill billSelected = null;

        for (Bill b : bills) {
            dateDiffStart = Math.abs(date.getTime() - b.getStartDate().getTime());
            dateDiffEnd = Math.abs(date.getTime() - b.getEndDate().getTime());

            if (dateDiffStartLast == 0) {
                dateDiffStartLast = dateDiffStart;
                dateDiffEndLast = dateDiffEnd;
                billSelected = b;
            }

            if (dateDiffStart < dateDiffStartLast || dateDiffEnd < dateDiffEndLast) {
                dateDiffStartLast = dateDiffStart;
                dateDiffEndLast = dateDiffEnd;
                billSelected = b;
            }
        }

        return billSelected;
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
        if (type == BillType.ELECTRIC) listBillElec.set(index, newBill);
        else listBillGas.set(index, newBill);
    }

    public void deleteBill(BillType type, int index) {
        if (type == BillType.ELECTRIC) listBillElec.remove(index);
        else listBillGas.remove(index);
    }

}
