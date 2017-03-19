package cmpt276.jade.carbontracker.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cmpt276.jade.carbontracker.utils.BillType;

/**
 *  Individual billing class for gas/electricity
 *  input validation expected to be done by calling class
 */
public class Bill implements Serializable {
    private Boolean gas = false;
    private double emissionTotal;   // Kg CO2
    private double emissionAvg;     // Kg CO2 / time
    private Date startDate;
    private Date endDate;
    private double input;
    private final double CALC_ELEC = 0.009;     // 9000Kg CO2 / GWh
    private final double CALC_GAS = 56.1;       // 56.1Kg CO2 / GJ

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
            emissionTotal = CALC_GAS * input;
        } else {
            // input in kWh
            emissionTotal = CALC_ELEC * input;
        }

        long dateDiff = endDate.getTime() - startDate.getTime();
        dateDiff = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);

        emissionAvg = emissionTotal / dateDiff;

        Log.i("calculateEmission", "dateDiff = "+dateDiff);
        Log.i("calculateEmission", "emissionTotal = "+emissionTotal+", emissionAvg = "+emissionAvg);
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
        calculateEmission();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date d) {
        endDate = d;
        calculateEmission();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setInput(double d) {
        input = d;
        calculateEmission();
    }

    public double getInput() {
        return input;
    }

    public Boolean isGas() {
        return gas;
    }

}

