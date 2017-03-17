package cmpt276.jade.carbontracker.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
}
