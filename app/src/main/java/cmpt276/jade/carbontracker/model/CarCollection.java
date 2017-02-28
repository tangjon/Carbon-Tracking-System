package cmpt276.jade.carbontracker.model;

import android.icu.text.StringPrepParseException;

import java.util.ArrayList;

/**
 * Created by tangj on 2/28/2017.
 */

public class CarCollection {
    private ArrayList<Car> carList;

    public CarCollection() {
        carList = new ArrayList<>();
    }

    public void add(Car car){
        carList.add(car);
    }

    // Returns ArrayList with the specified makers
    public ArrayList<Car> getCarMaker(String maker){
        ArrayList<Car> makerCarList = new ArrayList<>();
        for ( Car car: carList ) {
            if(car.getMake().equals(maker)){
                makerCarList.add(car);
            }
        }
        return makerCarList;
    }
    // Returns ArrayList with the specified models
    public ArrayList<Car> getCarModel(String model){
        ArrayList<Car> modelCarList = new ArrayList<>();
        for ( Car car: carList ) {
            if(car.getModel().equals(model)){
                modelCarList.add(car);
            }
        }
        return modelCarList;
    }

    // Returns ArrayList with the specified year
    public ArrayList<Car> getCarYear(int year){
        ArrayList<Car> yearCarList = new ArrayList<>();
        for ( Car car: carList ) {
            if(car.getYear() == year){
                yearCarList.add(car);
            }
        }
        return yearCarList;
    }

    public ArrayList<String> modelToStringList(){
        ArrayList<String> stringList = new ArrayList<>();
        for (Car car:
             carList) {
            stringList.add(car.getModel());
        }
        return stringList;
    }

    public ArrayList<String> makeToStringList(){
        ArrayList<String> stringList = new ArrayList<>();
        for (Car car:
                carList) {
            stringList.add(car.getMake());
        }
        return stringList;
    }

    public ArrayList<String> yearToStringList(){
        ArrayList<String> stringList = new ArrayList<>();
        for (Car car:
                carList) {
            stringList.add(Integer.toString(car.getYear()));
        }
        return stringList;
    }
}
