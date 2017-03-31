package cmpt276.jade.carbontracker.model;

import cmpt276.jade.carbontracker.enums.Language;
import cmpt276.jade.carbontracker.enums.MeasurementUnit;

/**
 * Created by Sean on 29/03/2017.
 */

public class Settings {

    private MeasurementUnit sillyMode;
    private Language languageMode;
    private static final double TREE_CARBON_ABSORBTION_PERHOUR  = 0.002485;

    // DEFAULT SETTINGS CONSTRUCTOR
    public Settings(){
        this.sillyMode = MeasurementUnit.REGULAR;
        this.languageMode = Language.ENGLISH;
    }

  public Settings(MeasurementUnit sillyMode, Language languageMode) {
    this.sillyMode = sillyMode;
    this.languageMode = languageMode;
  }

  public MeasurementUnit getSillyMode() {
        return sillyMode;
    }

    public void setSillyMode(MeasurementUnit sillyMode) {
        this.sillyMode = sillyMode;
    }

    public Language getLanguageMode() {
        return languageMode;
    }

    public void setLanguageMode(Language languageMode) {
        this.languageMode = languageMode;
    }

    public double calcTreeAbsorbtion(double emissions){
        return emissions / TREE_CARBON_ABSORBTION_PERHOUR;
    }

    public double calcEmissionsInKG(double treehours){
        return treehours * TREE_CARBON_ABSORBTION_PERHOUR;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "sillyMode=" + sillyMode +
                ", languageMode=" + languageMode +
                '}';
    }
}
