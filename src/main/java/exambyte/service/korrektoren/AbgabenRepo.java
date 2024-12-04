package exambyte.service.korrektoren;

import exambyte.aggregates.korrektoren.Abgabe;

import java.util.List;

public interface AbgabenRepo {
    Abgabe loadAbgabeWithId(int id);
    void saveAbgabe(Abgabe a);
    boolean hasAbgabeWithId(int id);
    List<Abgabe> loadAbgaben();
}
