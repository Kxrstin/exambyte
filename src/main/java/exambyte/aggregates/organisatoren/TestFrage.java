package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;

@Wertobjekt
public interface TestFrage {
    Integer getId();
    Integer getPunkte();
    String getTitel();
    String getFragestellung();
    String getBeschreibung();
    String getErklaerung();
    boolean istMcFrage();

}
