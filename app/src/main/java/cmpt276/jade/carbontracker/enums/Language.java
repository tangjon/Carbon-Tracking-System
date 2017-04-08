package cmpt276.jade.carbontracker.enums;

/*
* Enum: Determines the language
* */
public enum Language {
    SPANISH,ENGLISH, FRENCH;

  public static Language toEnum(int lOrdinal) {
    for (Language l: Language.values()) {
      if(lOrdinal == l.ordinal()){
        return l;
      }
    }
    return null;
  }
}
