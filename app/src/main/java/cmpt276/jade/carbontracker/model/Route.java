package cmpt276.jade.carbontracker.model;

public class Route {
    private String name;
    private  int HighWayDistance;
    private  int CityDistance;

    public Route(String name, int HighWayDistance,int CityDistance) {
        this.name=name;
        this.HighWayDistance=HighWayDistance;
        this.CityDistance=CityDistance;
    }

    public int getHighWayDistance() {
        return HighWayDistance;
    }
    public int getCityDistance() {
        return CityDistance;
    }

    public void setHighWayDistance(int HighWayDistance) {
        if (HighWayDistance < 0 ) {
            throw new IllegalArgumentException();
        }
        else {
            this.HighWayDistance=HighWayDistance;
        }
    }

    public void setCityDistance(int CityDistance) {
        if (CityDistance < 0 ) {
            throw new IllegalArgumentException();
        }
        else {
            this.CityDistance=CityDistance;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.equals("") ) {
            throw new IllegalArgumentException();
        }
        else {
            this.name=name;
        }
    }
}

//***********VERSON of hide the route instead of deleting it*********//
//***********Need to test in Monday meeting**************************//
/*

public class Route {
    private String name;
    private  int HighWayDistance;
    private  int CityDistance;
    private  String Selection;

    public Route(String name, int HighWayDistance,int CityDistance,String Selection) {
        this.name=name;
        this.HighWayDistance=HighWayDistance;
        this.CityDistance=CityDistance;
        this.Selection=Selection;

    }

    public int getHighWayDistance() {
        return HighWayDistance;
    }
    public int getCityDistance() {
        return CityDistance;
    }
    public String getSelection() {
        return Selection;
    }

    public void setHighWayDistance(int HighWayDistance) {
        if (HighWayDistance < 0 ) {
            throw new IllegalArgumentException();
        }
        else {
            this.HighWayDistance=HighWayDistance;
        }
    }

    public void setCityDistance(int CityDistance) {
        if (CityDistance < 0 ) {
            throw new IllegalArgumentException();
        }
        else {
            this.CityDistance=CityDistance;
        }
    }
    public void setSelection(String Selection) {
        this.Selection=Selection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.equals("") ) {
            throw new IllegalArgumentException();
        }
        else {
            this.name=name;
        }
    }
}*/
