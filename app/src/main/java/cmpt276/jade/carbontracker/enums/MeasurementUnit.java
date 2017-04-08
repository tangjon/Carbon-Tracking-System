package cmpt276.jade.carbontracker.enums;

/*
* Enum: Determines the unit of measurement
* */
public enum MeasurementUnit {
    REGULAR, TREES;

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
