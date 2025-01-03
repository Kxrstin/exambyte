package exambyte.service.korrektoren;

import exambyte.aggregates.korrektoren.Abgabe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AbgabenRepo extends CrudRepository<Abgabe, Integer> {
    List<Abgabe> findAll();
    Abgabe findById(int id);
}
