package cmpt276.jade.carbontracker.model;

/**
 * Created by Sean on 20/03/2017.
 */

public class Skytrain {

    private String nickName;
    private String boardingStation;
    private String skytrainLine;
    private int mode;
    private int position;

    public Skytrain(){

    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBoardingStation() {
        return boardingStation;
    }

    public void setBoardingStation(String boardingStation) {
        this.boardingStation = boardingStation;
    }

    public String getSkytrainLine() {
        return skytrainLine;
    }

    public void setSkytrainLine(String skytrainLine) {
        this.skytrainLine = skytrainLine;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
