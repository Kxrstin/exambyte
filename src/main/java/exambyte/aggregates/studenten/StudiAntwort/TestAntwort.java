package exambyte.aggregates.studenten.StudiAntwort;

interface TestAntwort {
    void addAntwort(String antwort);
    boolean isFreitextAufgabe();
    String getAntwort();
}
