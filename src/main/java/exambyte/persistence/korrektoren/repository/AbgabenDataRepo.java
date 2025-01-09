package exambyte.persistence.korrektoren.repository;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.persistence.korrektoren.data.AbgabeDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbgabenDataRepo extends CrudRepository<AbgabeDto, Integer> {
    List<AbgabeDto> findAll();
    AbgabeDto findById(int id);
    boolean existsById(int id);
    AbgabeDto save(Abgabe abgabe);
}
