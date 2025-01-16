package exambyte.persistence.organisatoren.repository;

import exambyte.persistence.organisatoren.data.TestFormularDto;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TestFormRepoDAO extends CrudRepository<TestFormularDto, Integer> {
    TestFormularDto save(TestFormularDto testForm);

    TestFormularDto findById(int id);

    List<TestFormularDto> findAll();

    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Modifying
    @Query("UPDATE test_formular SET startzeitpunkt = :start, endzeitpunkt = :ende, ergebniszeitpunkt = :ergebnis WHERE test_formular.id = :id")
    void updateZeitpunkte(@Param("start") LocalDateTime startzeitpunkt,
                          @Param("ende")LocalDateTime endzeitpunkt,
                          @Param("ergebnis")LocalDateTime ergebniszeitpunkt,
                          @Param("id") Integer id);
}
