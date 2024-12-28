package exambyte.aggregates.studenten.StudiAntwort;

interface TestAntwort {
    void addAntwort(String antwort);
    boolean isFreitextAufgabe();
    Integer getStudiId();
    Integer getAufgabeId();
    String getAntworten();
    void removeAntwort();
}
