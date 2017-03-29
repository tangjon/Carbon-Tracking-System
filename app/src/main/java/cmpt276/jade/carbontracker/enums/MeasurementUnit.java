package cmpt276.jade.carbontracker.enums;

/**
 * Created by tangj on 3/28/2017.
 * Class:
 * Description:
 * Bugs:
 */
public enum MeasurementUnit {
    REGULAR, COWS;

  public static MeasurementUnit toEnum(int measurementOrdinal) {
    for (MeasurementUnit m :
        MeasurementUnit.values()) {
      if(measurementOrdinal == m.ordinal()){
        return m;
      }
    }
    return null;
  }
}
