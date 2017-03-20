package cmpt276.jade.carbontracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 20/03/2017.
 */

public class SkytrainCollection {

    private List<Skytrain> trainList = new ArrayList<>();

    //add train to list
    public void addTrain(Skytrain train){
        trainList.add(train);
    }
    //Deletes train from list
    public void deleteTrain(int index){
        trainList.remove(index);
    }
    //edits train
    public void editTrain(Skytrain train ,int index){
        trainList.remove(index);
        trainList.add(index, train);
    }
    //get train from list
    public Skytrain getTrain(int index){
        return trainList.get(index);
    }
    //counts number of objects in list
    public int countTrains(){
        return trainList.size();
    }

    public List<Skytrain> getTrainList() {
        return trainList;
    }



}
