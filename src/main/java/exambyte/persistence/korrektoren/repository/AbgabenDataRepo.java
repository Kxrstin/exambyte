package exambyte.persistence.korrektoren.repository;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.persistence.korrektoren.data.AbgabeDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbgabenDataRepo extends CrudRepository<AbgabeDto, Integer> {
    List<AbgabeDto> findAll();
    AbgabeDto findById(int id);
    boolean existsById(int id);
    AbgabeDto save(Abgabe abgabe);

    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Query("SELECT * FROM abgabe WHERE id = :id")
    AbgabeDto findByIdWithLock(int id);
}
