package exambyte.aggregates.studenten.StudiAntwort;

import java.util.ArrayList;
import java.util.List;

class MCAntwort implements TestAntwort{
    private List<String> antwortWahl = new ArrayList<>();

    public void addAntwort(String antwort) {
        antwortWahl.add(antwort);
    }

    @Override
    public boolean isFreitextAufgabe() {
        return false;
    }

    public void removeAntwort(String antwort) {
        antwortWahl.remove(antwort);
    }

    public List<String> getAntwortWahl() {
        return antwortWahl;
    }
}
