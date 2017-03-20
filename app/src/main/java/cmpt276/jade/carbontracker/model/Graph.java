package cmpt276.jade.carbontracker.model;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cmpt276.jade.carbontracker.utils.BillType;

/**
 * Static class which generates data for graphing
 */
public class Graph {
    private static Emission emission;
    private static Utilities utilities;
    private static JourneyCollection journeyCollection;

    private Graph() {
        updateData();
    }

    private static void updateData(){
        emission = Emission.getInstance();
        utilities = emission.getUtilities();
        journeyCollection = emission.getJourneyCollection();
    }

    // Returns PieData used for generating pie graph in UI
    // mode : set to 0 if using all data, 1 if using specific date, 2 if within date range
    // Specific date arguments can be null if not used (see 'mode')
    // TODO: adapt to other emission-producing things
    public static PieData getPieData(String label, int mode,
                              Date dateSelected, Date dateRangeStart, Date dateRangeEnd) {

        updateData();
        List<PieEntry> pieEntries = new ArrayList<>();
        JourneyCollection buffer = getJourneys(mode, dateSelected, dateRangeStart, dateRangeEnd);

        JourneyData journeyData = new JourneyData(buffer);
        List<Bill> bills;
        float billSum = 0f;

        // Journeys
        for (int i = 0; i < buffer.countJourneys(); ++i)
            pieEntries.add(new PieEntry(journeyData.values[i], journeyData.nameRoute[i]));

        // Electric Bills
        bills = getBills(BillType.ELECTRIC, mode, dateSelected, dateRangeStart, dateRangeEnd);
        for (Bill b : bills) if (b != null) billSum += b.getEmissionAvg();
        if (bills.size() > 0 && bills.get(0) != null) pieEntries.add(new PieEntry(billSum, "Electricity"));

        // Gas Bills
        billSum = 0f;
        bills = getBills(BillType.GAS, mode, dateSelected, dateRangeStart, dateRangeEnd);
        for (Bill b : bills) if (b != null) billSum += b.getEmissionAvg();
        if (bills.size() > 0 && bills.get(0) != null) pieEntries.add(new PieEntry(billSum, "Gas"));

        PieDataSet dataSet = new PieDataSet(pieEntries, label);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        return new PieData(dataSet);
    }

    // Returns BarData used for generating bar graph in UI
    // mode : set to 0 if using all data, 1 if using specific date, 2 if within date range
    // Specific date arguments can be null if not used (see 'mode')
    // TODO: adapt to other emission-producing things
    public static BarData getBarData(String label, int mode,
                                     Date dateSelected, Date dateRangeStart, Date dateRangeEnd) {
        updateData();
        List<BarEntry> barEntries = new ArrayList<>();
        JourneyCollection buffer = getJourneys(mode, dateSelected, dateRangeStart, dateRangeEnd);

        final JourneyData journeyData = new JourneyData(buffer);
        List<Bill> bills;
        float billSum = 0f;

        // Journeys
        barEntries.add(new BarEntry(0, journeyData.values, "Journeys"));

        // Electric Bills
        bills = getBills(BillType.ELECTRIC, mode, dateSelected, dateRangeStart, dateRangeEnd);
        for (Bill b : bills) if (b != null) billSum += b.getEmissionAvg();
        if (bills.size() > 0) {
            barEntries.add(new BarEntry(1, new float[] {billSum}, "Electricity"));
        }

        // Gas Bills
        billSum = 0f;
        bills = getBills(BillType.GAS, mode, dateSelected, dateRangeStart, dateRangeEnd);
        for (Bill b : bills) if (b != null) billSum += b.getEmissionAvg();
        if (bills.size() > 0) {
            barEntries.add(new BarEntry(2, new float[] {billSum}, "Gas"));
        }

        BarDataSet dataSet = new BarDataSet(barEntries, label);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        return new BarData(dataSet);
    }

    private static List<Bill> getBills(
            BillType type, int mode, Date dateSelected, Date dateRangeStart, Date dateRangeEnd) {

        List<Bill> bills;

        if (mode == 0) {
            if (type == BillType.ELECTRIC) bills = utilities.getListBillElec();
            else bills = utilities.getListBillGas();
        } else if (mode == 1) {
            bills = utilities.getBillsOnDay(dateSelected, type);
            if (bills.size() < 1) bills.add(utilities.getNearestBill(dateSelected, type));
        } else {
            bills = utilities.getBillsWithinRange(dateRangeStart, dateRangeEnd, type);
            if (bills.size() < 1) bills.add(utilities.getNearestBill(dateSelected, type));
        }

        return bills;
    }

    private static JourneyCollection getJourneys(
            int mode, Date dateSelected, Date dateRangeStart, Date dateRangeEnd) {

        JourneyCollection buffer = new JourneyCollection();

        if (mode == 0) buffer = journeyCollection;
        else if (mode == 1) {
            for (int i = 0; i < journeyCollection.countJourneys(); ++i)
                if (journeyCollection.getJourney(i).getDate().equals(dateSelected))     // *****
                    buffer.addJourney(journeyCollection.getJourney(i));
        } else {
            Journey j;
            for (int i = 0; i < journeyCollection.countJourneys(); ++i) {
                /*j = journeyCollection.getJourney(i);                                  // *****
                Date d = j.getDate();
                if (d.equals(dateRangeStart) || d.equals(dateRangeEnd)
                       || (d.before(dateRangeEnd) && d.after(dateRangeStart)))
                   buffer.addJourney(j);
                */
                buffer.addJourney(journeyCollection.getJourney(i));
            }
        }

        return buffer;
    }

    private static class JourneyData {
        public String nameRoute[];
        public String nameVehicle[];
        public String date[];
        public float values[];
        public double distance[];
        private JourneyCollection journeyCollection;

        JourneyData(JourneyCollection jc) {
            journeyCollection = jc;
            int size = journeyCollection.countJourneys();
            nameRoute = new String[size];
            nameVehicle = new String[size];
            date = new String[size];
            values = new float[size];
            distance = new double[size];

            Journey j;
            for (int i = 0; i < size; ++i) {
                j = journeyCollection.getJourney(i);
                nameRoute[i] = j.getName();
                nameVehicle[i] = j.getTransType().getCar().getNickname();
                date[i] = j.getDate();
                values[i] = (float) j.getTotalTravelledEmissions();
                distance[i] = j.getTotalDriven();
            }
        }
    }
}
