package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;

@Wertobjekt
public interface TestAufgabe {
    String getAufgabe();
    int getPunktzahl();
    Integer getId();
    boolean isFreitextAufgabe();
    Integer getStudiTest();
}
