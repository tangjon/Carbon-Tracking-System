package cmpt276.jade.carbontracker.model;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cmpt276.jade.carbontracker.utils.BillType;

/**
 * Manages graph generation
 */
public class Graph {
    private Emission emission;
    private Utilities utilities;
    private JourneyCollection journeyCollection;
    private Date dateSelected;
    private Date dateRangeStart;
    private Date dateRangeEnd;
    private Calendar dateNew;

    public static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public Graph(){
        emission = Emission.getInstance();
        utilities = emission.getUtilities();
        journeyCollection = emission.getJourneyCollection();
        dateNew = Calendar.getInstance();

        dateSelected = dateNew.getTime();
    }

    // Returns PieData used for generating pie graph in UI
    // This version uses all data
    // TODO: adapt to other emission-producing things
    public PieData getPieData(String label) {
        List<PieEntry> pieEntries = new ArrayList<>();
        RouteData routeData = new RouteData(journeyCollection);
        List<Bill> bills;
        float billSum = 0f;

        // Journeys
        for (int i = 0; i < journeyCollection.countJourneys(); ++i)
            pieEntries.add(new PieEntry(routeData.values[i], routeData.nameRoute[i]));

        // Electric Bills
        bills = utilities.getListBillElec();
        for (Bill b : bills)
            billSum += b.getEmissionAvg();
        pieEntries.add(new PieEntry(billSum, "Electricity"));

        // Gas Bills
        bills = utilities.getListBillGas();
        for (Bill b : bills)
            billSum += b.getEmissionAvg();
        pieEntries.add(new PieEntry(billSum, "Gas"));

        PieDataSet dataSet = new PieDataSet(pieEntries, label);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        return new PieData(dataSet);
    }

    // This version uses only data occurring on a specific date
    // TODO: adapt to other emission-producing things
    public PieData getPieData(String label, Date dateSelected) {
        List<PieEntry> pieEntries = new ArrayList<>();
        JourneyCollection buffer = new JourneyCollection();

        // TODO: use Date objects from Journey
        for (int i = 0; i < journeyCollection.countJourneys(); ++i)
            // if (journeyCollection.getJourney(i).getDate().equals(dateSelected))
                buffer.addJourney(journeyCollection.getJourney(i));

        RouteData routeData = new RouteData(buffer);
        List<Bill> bills;
        float billSum = 0f;

        // Journeys
        for (int i = 0; i < buffer.countJourneys(); ++i)
            pieEntries.add(new PieEntry(routeData.values[i], routeData.nameRoute[i]));

        // Electric Bills
        bills = utilities.getBillsOnDay(dateSelected, BillType.ELECTRIC);
        for (Bill b : bills)
            billSum += b.getEmissionAvg();
        pieEntries.add(new PieEntry(billSum, "Electricity"));

        // Gas Bills
        bills = utilities.getBillsOnDay(dateSelected, BillType.GAS);
        for (Bill b : bills)
            billSum += b.getEmissionAvg();
        pieEntries.add(new PieEntry(billSum, "Gas"));

        PieDataSet dataSet = new PieDataSet(pieEntries, label);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        return new PieData(dataSet);
    }

    // This version uses only data occurring within a specific range
    // TODO: adapt to other emission-producing things
    public PieData getPieData(String label, Date dateRangeStart, Date dateRangeEnd) {
        List<PieEntry> pieEntries = new ArrayList<>();
        JourneyCollection buffer = new JourneyCollection();
        Journey j;

        // TODO: use Date objects from Journey
        for (int i = 0; i < journeyCollection.countJourneys(); ++i) {
            /*j = journeyCollection.getJourney(i);
            Date d = j.getDate();
            if (d.equals(dateRangeStart) || d.equals(dateRangeEnd)
                    || (d.before(dateRangeEnd) && d.after(dateRangeStart)))
                buffer.addJourney(j);
            */
            buffer.addJourney(journeyCollection.getJourney(i));
        }

        RouteData routeData = new RouteData(buffer);
        List<Bill> bills;
        float billSum = 0f;

        // Journeys
        for (int i = 0; i < buffer.countJourneys(); ++i)
            pieEntries.add(new PieEntry(routeData.values[i], routeData.nameRoute[i]));

        // Electric Bills
        bills = utilities.getBillsWithinRange(dateRangeStart, dateRangeEnd, BillType.ELECTRIC);
        for (Bill b : bills)
            billSum += b.getEmissionAvg();
        pieEntries.add(new PieEntry(billSum, "Electricity"));

        // Gas Bills
        bills = utilities.getBillsWithinRange(dateRangeStart, dateRangeEnd, BillType.GAS);
        for (Bill b : bills)
            billSum += b.getEmissionAvg();
        pieEntries.add(new PieEntry(billSum, "Gas"));

        PieDataSet dataSet = new PieDataSet(pieEntries, label);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        return new PieData(dataSet);
    }

    public void updateData() {
        utilities = emission.getUtilities();
        journeyCollection = emission.getJourneyCollection();
    }

    public Date getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(Date dateSelected) {
        this.dateSelected = dateSelected;
    }

    public Date getDateRangeStart() {
        return dateRangeStart;
    }

    public void setDateRangeStart(Date dateRangeStart) {
        this.dateRangeStart = dateRangeStart;
    }

    public Date getDateRangeEnd() {
        return dateRangeEnd;
    }

    public void setDateRangeEnd(Date dateRangeEnd) {
        this.dateRangeEnd = dateRangeEnd;
    }

    private class RouteData {
        public List<Entry> entries;
        public String nameRoute[];
        public String nameVehicle[];
        public String date[];
        public float values[];
        public double distance[];
        private JourneyCollection journeyCollection;

        RouteData(JourneyCollection jc) {
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
                nameRoute[i] = j.getRoute().getName();
                nameVehicle[i] = j.getTransType().getCar().getNickname();
                date[i] = j.getDate();
                values[i] = (float) j.getTotalTravelledEmissions();
                distance[i] = j.getTotalDriven();
            }
        }
    }
}
