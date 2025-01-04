package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;

import java.util.List;

@Wertobjekt
public interface TestFrage {
    Integer getId();
    void setId(Integer id);
    void setTestFormular(Integer testFormular);
    Integer getPunkte();
    String getTitel();
    String getFragestellung();
    String getErklaerung();
    boolean istMcFrage();
    List<McAntwortOrga> getMcAntwortOrga();
}
