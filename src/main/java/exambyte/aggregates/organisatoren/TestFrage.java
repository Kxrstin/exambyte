package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;

import java.util.List;

@Wertobjekt
public interface TestFrage {
    Integer getId();
    Integer getPunkte();
    String getTitel();
    String getFragestellung();
    String getErklaerung();
    boolean istMcFrage();
    List<McAntwort> getAntworten();
}
