package exambyte.aggregates.studenten.StudiAntwort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MCAntwort implements TestAntwort{
    private List<String> antwortWahl = new ArrayList<>();

    public void addAntwort(String antwort) {
        String listeOhneKlammern = antwort.substring(1, antwort.length()-1);
        Collections.addAll(antwortWahl, listeOhneKlammern.split(", "));
    }

    @Override
    public boolean isFreitextAufgabe() {
        return false;
    }
    public void removeAntwort(String antwort) {
        antwortWahl.remove(antwort);
    }
    public String getAntwort() {
        return antwortWahl.toString();
    }
}
