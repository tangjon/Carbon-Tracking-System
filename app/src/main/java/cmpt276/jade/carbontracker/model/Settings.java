package cmpt276.jade.carbontracker.model;

import cmpt276.jade.carbontracker.enums.Language;
import cmpt276.jade.carbontracker.enums.MeasurementUnit;

/**
 * Created by Sean on 29/03/2017.
 */

public class Settings {

    private MeasurementUnit sillyMode;
    private Language languageMode;

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

}
