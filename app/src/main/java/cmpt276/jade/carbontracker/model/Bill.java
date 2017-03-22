package cmpt276.jade.carbontracker.model;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cmpt276.jade.carbontracker.utils.BillType;

/**
 *  Individual billing class for gas/electricity
 *  input validation expected to be done by calling class
 */
public class Bill implements Serializable {
    private BillType type;
    private double emissionTotal;   // Kg CO2
    private double emissionAvg;     // Kg CO2 / time
    private Date startDate;
    private Date endDate;
    private double input;
    private final double CALC_ELEC = 0.009;     // 9000Kg CO2 / GWh
    private final double CALC_GAS = 56.1;       // 56.1Kg CO2 / GJ
    private int numResidents = Emission.getInstance().getUtilities().getNumResidents();

    public Bill(BillType t, Date startDate, Date endDate, double input) {
        type = t;
        this.startDate = startDate;
        this.endDate = endDate;
        this.input = input;

        if (startDate != null && endDate != null) calculateEmission();
    }

    private void calculateEmission() {
        if (type == BillType.GAS) {
            // input in GJ
            emissionTotal = CALC_GAS * input;
        } else {
            // input in kWh
            emissionTotal = CALC_ELEC * input;
        }

        long dateDiff = endDate.getTime() - startDate.getTime();
        dateDiff = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);

        emissionAvg = emissionTotal / dateDiff;
    }

    public BillType getBillType() {
        return type;
    }

    public double getEmissionTotal() {
        return emissionTotal;
    }

    public double getEmissionAvg() {
        return emissionAvg;
    }

    public void setStartDate(Date d) {
        startDate = d;
        if (startDate != null && endDate != null) calculateEmission();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date d) {
        endDate = d;
        if (startDate != null && endDate != null) calculateEmission();
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

    public int getNumResidents() {
        return numResidents;
    }

    public void setNumResidents(int numResidents) {
        this.numResidents = numResidents;
    }
    public Boolean isGas() {
        return (type == BillType.GAS);
    }

}

