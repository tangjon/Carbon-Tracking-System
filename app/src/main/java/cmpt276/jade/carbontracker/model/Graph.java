package cmpt276.jade.carbontracker.model;

import android.util.Log;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cmpt276.jade.carbontracker.enums.BillType;
import cmpt276.jade.carbontracker.enums.DateMode;
import cmpt276.jade.carbontracker.enums.GroupMode;
import cmpt276.jade.carbontracker.enums.Transport;

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

    private static void updateData() {
        emission = Emission.getInstance();
        utilities = emission.getUtilities();
        journeyCollection = emission.getJourneyCollection();
    }

    // Returns PieData used for generating pie graph in UI
    // mode : set to 0 if using all data, 1 if using specific date, 2 if within date range
    // Specific date arguments can be null if not used (see 'mode')
    public static PieData getPieData(String label, DateMode mode, GroupMode groupMode,
                                     Date dateSelected, Date dateRangeStart, Date dateRangeEnd) {

        updateData();
        List<PieEntry> pieEntries = new ArrayList<>();
        JourneyCollection buffer = getJourneys(mode, dateSelected, dateRangeStart, dateRangeEnd);
        List<PieEntry> bufferEntries = new ArrayList<>();

        JourneyData journeyData = new JourneyData(buffer);
        List<Bill> bills;
        float billSum = 0f;

        // Journeys
        for (int i = 0; i < buffer.countJourneys(); ++i) {
            //pieEntries.add(new PieEntry(journeyData.values[i], journeyData.nameVehicle[i]));
            addToGroup(pieEntries, groupMode, buffer.getJourney(i));
        }

        // Electric Bills
        bills = getBills(BillType.ELECTRIC, mode, dateSelected, dateRangeStart, dateRangeEnd);
        for (Bill b : bills) if (b != null)
            billSum += b.getEmissionAvg();
        if (bills.size() > 0 && bills.get(0) != null)
            pieEntries.add(new PieEntry(billSum, "Electricity"));

        // Gas Bills
        billSum = 0f;
        bills = getBills(BillType.GAS, mode, dateSelected, dateRangeStart, dateRangeEnd);
        for (Bill b : bills)
            if (b != null)
                billSum += b.getEmissionAvg();
        if (bills.size() > 0 && bills.get(0) != null)
            pieEntries.add(new PieEntry(billSum, "Gas"));

        PieDataSet dataSet = new PieDataSet(pieEntries, label);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        return new PieData(dataSet);
    }

    // Returns BarData used for generating bar graph in UI
    // mode : set to 0 if using all data, 1 if using specific date, 2 if within date range
    // Specific date arguments can be null if not used (see 'mode')
    public static BarData getBarData(String label, DateMode mode,
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
        for (Bill b : bills)
            if (b != null)
                billSum += b.getEmissionAvg();
        if (bills.size() > 0)
            barEntries.add(new BarEntry(1, new float[]{billSum}, "Electricity"));

        // Gas Bills
        billSum = 0f;
        bills = getBills(BillType.GAS, mode, dateSelected, dateRangeStart, dateRangeEnd);
        for (Bill b : bills)
            if (b != null)
                billSum += b.getEmissionAvg();
        if (bills.size() > 0)
            barEntries.add(new BarEntry(2, new float[]{billSum}, "Gas"));

        BarDataSet dataSet = new BarDataSet(barEntries, label);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        return new BarData(dataSet);
    }

    private static List<Bill> getBills(
            BillType type, DateMode mode, Date dateSelected, Date dateRangeStart, Date dateRangeEnd) {

        List<Bill> bills;

        if (mode == DateMode.RANGE) {
            if (type == BillType.ELECTRIC)
                bills = utilities.getListBillElec();
            else
                bills = utilities.getListBillGas();
        } else if (mode == DateMode.SINGLE) {
            bills = utilities.getBillsOnDay(dateSelected, type);
            if (bills.size() < 1)
                bills.add(utilities.getNearestBill(dateSelected, type));
        } else {
            bills = utilities.getBillsWithinRange(dateRangeStart, dateRangeEnd, type);
            if (bills.size() < 1)
                bills.add(utilities.getNearestBill(dateSelected, type));
        }

        return bills;
    }

    public static JourneyCollection getJourneys(
            DateMode mode, Date dateSelected, Date dateRangeStart, Date dateRangeEnd) {

        JourneyCollection buffer = new JourneyCollection();
        Journey j;

        Log.i("getJourneys", "mode = " + mode);

        if (mode == DateMode.ALL)
            buffer = journeyCollection;
        else if (mode == DateMode.SINGLE) {
            for (int i = 0; i < journeyCollection.countJourneys(); ++i) {
                j = journeyCollection.getJourney(i);

                Log.i("getJourneys", "compareDates returned " + compareDates(j.getDateObj(), dateSelected) + ", needed 0");
                if (j.getTotalTravelledEmissions() > 0.0 &&
                        compareDates(j.getDateObj(), dateSelected) == 0) {
                    Log.i("GetJourneys", "added journey " + journeyCollection.getJourney(i).toString());
                    buffer.addJourney(journeyCollection.getJourney(i));
                }
            }
        } else {
            for (int i = 0; i < journeyCollection.countJourneys(); ++i) {
                j = journeyCollection.getJourney(i);
                Date d = j.getDateObj();

                if (j.getTotalTravelledEmissions() > 0.0 &&
                        compareDates(d, dateRangeStart) > -1 && compareDates(d, dateRangeEnd) < 1) {
                    buffer.addJourney(j);
                    Log.i("GetJourneys", "added journey " + j.toString());
                }
            }
        }

        return buffer;
    }

    /**
     * compares two dates while ignoring time portion
     * returns  -1  :   date1 < date2
     *          0   :   date1 = date2
     *          1   :   date1 > date2
     */
    public static int compareDates(Date date1, Date date2) {
        if (date1 == null || date2 == null) return 0;

        date1 = makeTimeMidnight(date1);
        date2 = makeTimeMidnight(date2);

        Log.i("compareDates", "" + date1.toString() + " ? " + date2.toString());

        return date1.compareTo(date2);
    }

    public static Date makeTimeMidnight(Date d) {
        Calendar c = Calendar.getInstance();

        c.setTime(d);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);

        return c.getTime();
    }

    private static void addToGroup(List<PieEntry> pieEntries, GroupMode mode, Journey journey) {
        Transportation transport = journey.getTransType();

        for (int i = 0; i < pieEntries.size(); ++i) {
            PieEntry entry = pieEntries.get(i);

            switch (mode) {
                case TRANSPORTATION:
                    if (transport.getCar() != null) {
                        if (entry.getLabel().equals(transport.getCar().getNickName())){
                            entry.setY(entry.getValue()
                                    + (float) journey.getTotalTravelledEmissions());
                        } else
                            pieEntries.add(new PieEntry(
                                    (float) journey.getTotalTravelledEmissions(),
                                    transport.getCar().getNickName()));
                    } else if (transport.getBus() != null) {
                        if (entry.getLabel().equals(transport.getBus().getNickName())){
                            entry.setY(entry.getValue()
                                    + (float) journey.getTotalTravelledEmissions());
                        } else
                            pieEntries.add(new PieEntry(
                                    (float) journey.getTotalTravelledEmissions(),
                                    transport.getBus().getNickName()));
                    } else if (transport.getSkytrain() != null) {
                        if (entry.getLabel().equals(transport.getSkytrain().getNickName())){
                            entry.setY(entry.getValue()
                                    + (float) journey.getTotalTravelledEmissions());
                        } else
                            pieEntries.add(new PieEntry(
                                    (float) journey.getTotalTravelledEmissions(),
                                    transport.getSkytrain().getNickName()));
                    } else {
                        if (entry.getLabel().equals("Walk/Bike")){
                            entry.setY(entry.getValue()
                                    + (float) journey.getTotalTravelledEmissions());
                        } else
                            pieEntries.add(new PieEntry(
                                    (float) journey.getTotalTravelledEmissions(),
                                    transport.getBus().getNickName()));
                    }

                    break;

                case ROUTE:

                    break;

            }
        }
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
            Transportation t;
            for (int i = 0; i < size; ++i) {
                j = journeyCollection.getJourney(i);
                t = j.getTransType();
                nameRoute[i] = j.getName();
                if (t.getCar() != null) {
                    nameVehicle[i] = t.getCar().getNickName();
                } else if (t.getSkytrain() != null) {
                    nameVehicle[i] = t.getSkytrain().getNickName();
                } else if (t.getBus() != null) {
                    nameVehicle[i] = t.getBus().getNickName();
                } else {
                    nameVehicle[i] = "Walk/Bike";
                }
                if (j.getDateObj() != null)
                    date[i] = Emission.DATE_FORMAT.format(j.getDateObj());
                else date[i] = "n/a";
                values[i] = (float) j.getTotalTravelledEmissions();
                distance[i] = j.getTotalDriven();
            }
        }
    }
}
