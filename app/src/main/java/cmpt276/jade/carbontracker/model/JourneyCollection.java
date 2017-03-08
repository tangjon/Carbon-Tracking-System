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
    public Journey getJourney(int index){
        return journeyList.get(index);
    }
    //counts number of objects in list
    public int countJourneys(){
        return journeyList.size();
    }

    public List<Journey> getJourneyList() {
        return journeyList;
    }

    //get Journey Details
    public String[] getJourneyDetails(){
        String[] names = new String[countJourneys()];
        for (int i = 0; i < countJourneys(); i++) {
            Journey journey = getJourney(i);
            names[i] = journey.getName()+ " "+ journey.getDate() +"\n" + journey.getCar().getNickname()+"\n"+ journey.getRoute().getName();
        }
        return names;
    }
}
