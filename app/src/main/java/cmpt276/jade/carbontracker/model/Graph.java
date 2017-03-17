package cmpt276.jade.carbontracker.model;

/**
 * Manages graph generation
 */
public class Graph {
    private Emission emission;
    private Utilities utilities;
    private JourneyCollection journeyCollection;

    public Graph(){
        emission = Emission.getInstance();
        utilities = emission.getUtilities();
        journeyCollection = emission.getJourneyCollection();


    }
}
