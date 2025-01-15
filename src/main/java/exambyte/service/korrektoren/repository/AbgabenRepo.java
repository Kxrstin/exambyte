package exambyte.service.korrektoren.repository;

import exambyte.aggregates.korrektoren.Abgabe;

import java.util.List;

public interface AbgabenRepo {
    List<Abgabe> findAll();
    Abgabe findById(int id);
    boolean existsById(int abgabeId);
    Abgabe save(Abgabe abgabe);

    Abgabe findByIdWithLock(int id);
}
