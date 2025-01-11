package exambyte.aggregates.studenten.StudiAntwort;

interface TestAntwort {
    boolean isFreitextAufgabe();
    Integer getStudiId();
    int getAufgabeId();
    String getAntworten();
}
