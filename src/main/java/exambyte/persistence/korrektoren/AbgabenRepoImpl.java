package exambyte.persistence.korrektoren;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.service.korrektoren.AbgabenRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AbgabenRepoImpl implements AbgabenRepo {
    Map<Integer, Abgabe> aufgabenMap = new HashMap<>();

    public AbgabenRepoImpl() {
        aufgabenMap.put(1, new Abgabe(1, "Programmierpraktikum 2 Test Woche 4", "Womit testet man die Onion Architektur.", "Mit ArchTests."));
        aufgabenMap.put(2, new Abgabe(2, "Mathematik für Informatik 3 Test Woche 5", "Was ist 2 + 2?", "4"));
        aufgabenMap.put(3, new Abgabe(3, "Wissenschaftliches Arbeiten", "Was ist toll?", "Alle Kaninchen sind toll."));
    }

    @Override
    public Abgabe loadAbgabeWithId(int id) {
        return aufgabenMap.get(id);
    }

    @Override
    public void saveAbgabe(Abgabe aufgabe) {
        aufgabenMap.put(aufgabe.getId(), aufgabe);
    }

    @Override
    public boolean hasAbgabeWithId(int id) {
        return aufgabenMap.containsKey(id);
    }

    @Override
    public List<Abgabe> loadAbgaben() {
        List<Abgabe> aufgabenListe = new ArrayList<>();
        for(Map.Entry<Integer, Abgabe> aufgabeEntry: aufgabenMap.entrySet()) {
            Abgabe aufgabe = aufgabeEntry.getValue();
            aufgabenListe.add(aufgabe);
        }
        return aufgabenListe;
    }
}
