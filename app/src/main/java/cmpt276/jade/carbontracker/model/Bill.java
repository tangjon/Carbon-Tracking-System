package cmpt276.jade.carbontracker.model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Bill {
    private Boolean gas = false;
    private double emissionTotal;   // Kg CO2
    private double emissionAvg;     // Kg CO2 / time
    private Date startDate;
    private Date endDate;
    private double input;
    private final double CALC_GAS = 0.009;    // 9000Kg CO2 / GWh
    private final double CALC_ELEC = 56.1;    // 56.1Kg CO2 / GJ

    public Bill(BillType t, Date startDate, Date endDate, double input) {
        if (t == BillType.GAS) gas = true;
        this.startDate = startDate;
        this.endDate = endDate;
        this.input = input;

        calculateEmission();
    }

    private void calculateEmission() {
        if (gas) {
            // input in GJ
            emissionTotal = CALC_GAS / input;
        } else {
            // input in kWh
            emissionTotal = CALC_ELEC / input;
        }

        long dateDiff = endDate.getTime() - startDate.getTime();
        dateDiff = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);

        emissionAvg = emissionTotal / dateDiff;
    }

    public BillType getBillType() {
        if (gas) return BillType.GAS;
        else return BillType.ELECTRIC;
    }

    public double getEmissionTotal() {
        return emissionTotal;
    }

    public double getEmissionAvg() {
        return emissionAvg;
    }

    public void setStartDate(Date d) {
        startDate = d;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date d) {
        endDate = d;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setInput(double d) {
        input = d;
    }

    public double getInput() {
        return input;
    }

}

