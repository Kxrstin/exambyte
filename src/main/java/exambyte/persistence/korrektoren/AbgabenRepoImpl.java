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
        aufgabenMap.put(101, new Abgabe(101, "Programmierpraktikum 2 Test Woche 4", "Womit testet man die Onion Architektur.", "Mit ArchTests.", 2));
        aufgabenMap.put(102, new Abgabe(102, "Mathematik für Informatik 3 Test Woche 5", "Was ist 2 + 2?", "4", 1));
        aufgabenMap.put(103, new Abgabe(103, "Wissenschaftliches Arbeiten", "Was ist toll?", "Alle Kaninchen sind toll.", 2));
        aufgabenMap.put(104, new Abgabe(104, "Programmierpraktikum 2 Test Woche 4", "Womit testet man die Onion Architektur.", "Man nutzt dafür WebMVCTests.", 3));
        aufgabenMap.put(105, new Abgabe(105, "Mathematik für Informatik 3 Test Woche 5", "Was ist 2 + 2?", "8", 1));
        aufgabenMap.put(106, new Abgabe(106, "Wissenschaftliches Arbeiten", "Was ist toll?", "Alle Enten sind toll.", 2));
        aufgabenMap.put(107, new Abgabe(107, "Programmierpraktikum 2 Test Woche 4", "Womit testet man die Onion Architektur.", "Keine Ahnung.", 3));
        aufgabenMap.put(108, new Abgabe(108, "Mathematik für Informatik 3 Test Woche 5", "Was ist 2 + 2?", "4", 1));
        aufgabenMap.put(109, new Abgabe(109, "Wissenschaftliches Arbeiten", "Wofür nutzt man Latex", "Zum Erstellen von Dokumenten.", 2));
        aufgabenMap.put(201, new Abgabe(201, "Programmierpraktikum 2 Test Woche 4", "Womit testet man die Onion Architektur.", "Mit ArchTests.", 3));
        aufgabenMap.put(202, new Abgabe(202, "Mathematik für Informatik 3 Test Woche 5", "Was ist 2 + 2?", "4", 1));
        aufgabenMap.put(203, new Abgabe(203, "Wissenschaftliches Arbeiten", "Wofür nutzt man Latex?", "Damit kann man Tabellen und so machen, wie in Word.", 2));
        aufgabenMap.put(204, new Abgabe(204, "Programmierpraktikum 2 Test Woche 4", "Was ist ein WebMvcTest?", "Damit kann man Controller testen.", 3));
        aufgabenMap.put(205, new Abgabe(205, "Mathematik für Informatik 3 Test Woche 5", "Was ist 2 + 3?", "8", 1));
        aufgabenMap.put(206, new Abgabe(206, "Wissenschaftliches Arbeiten", "Was ist toll?", "Alle Enten sind toll.", 2));
        aufgabenMap.put(207, new Abgabe(207, "Programmierpraktikum 2 Test Woche 4", "Womit testet man die Onion Architektur.", "Keine Ahnung.", 3));
        aufgabenMap.put(208, new Abgabe(208, "Mathematik für Informatik 3 Test Woche 5", "Was ist 2 + 9?", "11", 1));
        aufgabenMap.put(209, new Abgabe(209, "Wissenschaftliches Arbeiten", "Sag einen Witz?", "Keine Ahnung.", 2));

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
