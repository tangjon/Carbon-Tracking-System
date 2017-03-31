package cmpt276.jade.carbontracker.enums;

/**
 * Created by tangj on 3/28/2017.
 * Class:
 * Description:
 * Bugs:
 */
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
