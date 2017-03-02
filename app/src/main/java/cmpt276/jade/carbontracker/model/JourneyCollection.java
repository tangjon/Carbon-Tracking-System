package cmpt276.jade.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 02/03/2017.
 */

public class JourneyCollection {
    private List<Journey>  journeyList = new ArrayList<>();

    //add journey to list
    public void addJourney(Journey journey){
        journeyList.add(journey);
    }
    //Deletes journey entry from list
    public void deleteJourney(int index){
        journeyList.remove(index);
    }
    //edits the journey
    public void editJourney(Journey journey,int index){
        journeyList.remove(index);
        journeyList.add(index, journey);
    }
    //get journey from journeyList
    public void getJourney(int index){
        journeyList.get(index);
    }

}
