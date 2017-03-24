package cmpt276.jade.carbontracker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Car Collection Class that stores multiple Car Objects from Car Model
 */

public class CarCollection {
    private List<Car> carList;

    public CarCollection() {
        carList = new ArrayList<>();
    }

    public CarCollection(List<Car> carList) {
        this.carList = carList;
    }

    public void add(Car car) {
        carList.add(car);
    }

    public boolean remove(Car car) {
        UUID KEY = car.getKEY();
        if (!carList.isEmpty()) {
            for (int i = 0; i < carList.size(); i++) {
                if (carList.get(i).getKEY() == car.getKEY()) {
                    carList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public void remove(int index) {
        carList.remove(index);
    }

    // Return specific collection containing the make, and model
    public CarCollection search(String make, String model) {
        CarCollection collection = new CarCollection();
        for (Car car : carList) {
            if (car.getMake().equals(make) && car.getModel().equals(model)) {
                collection.add(car);
            }
        }
        return collection;
    }

    // Returns CarCollection with the specified makers
    public CarCollection getCarMaker(String maker) {
        CarCollection collection = new CarCollection();
        for (Car car : carList) {
            if (car.getMake().equals(maker)) {
                collection.add(car);
            }
        }
        return collection;
    }

    // Returns CarCollection with the specified models
    public CarCollection getCarModel(String model) {
        CarCollection collection = new CarCollection();
        for (Car car : carList) {
            if (car.getModel().equals(model)) {
                collection.add(car);
            }
        }
        return collection;
    }

    // Returns ArrayList with the specified year
    public CarCollection getCarYear(int year) {
        CarCollection collection = new CarCollection();
        for (Car car : carList) {
            if (car.getYear() == year) {
                collection.add(car);
            }
        }
        return collection;
    }


    public List<String> modelToStringList() {
        List<String> stringList = new ArrayList<>();
        for (Car car :
                carList) {
            stringList.add(car.getModel());
        }
        return stringList;
    }

    public List<String> makeToStringList() {
        List<String> stringList = new ArrayList<>();
        for (Car car : carList) {
            stringList.add(car.getMake());
        }
        return stringList;
    }

    public List<String> yearToStringList() {
        List<String> stringList = new ArrayList<>();
        for (Car car :
                carList) {
            stringList.add(Integer.toString(car.getYear()));
        }
        return stringList;
    }

    public List<Car> toList() {
        return carList;
    }

    public Car getCarByKey(String key) {
        for (Car car :
                carList) {
            if (car.getKEY().toString().equals(key)) {
                return car;
            }
        }
        return null;
    }

    public boolean setCarList(List<Car> lst) {
        if (lst != null) {
            carList = lst;
            return true;
        }
        return false;
    }

    public int getIndexOf(Car searchCar) {
        int i = 0;
        for (Car car : carList) {
            if (searchCar.toStringNoKey().equals(car.toStringNoKey())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean updateCarInfo(Car userEditCar) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getKEY() == userEditCar.getKEY()) {
                carList.set(i, userEditCar);
                return true;
            }
        }
        return false;
    }

    public Car getCar(int index) {
        return carList.get(index);
    }


    public int getSize() {
        return carList.size();
    }
}
