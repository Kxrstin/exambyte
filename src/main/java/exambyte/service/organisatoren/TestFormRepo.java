package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TestFormRepo extends CrudRepository<TestFormular, Integer> {
    TestFormular save(TestFormular testForm);
    TestFormular findById(int id);
    List<TestFormular> findAll();

    @Modifying
    @Query("UPDATE test_formular SET startzeitpunkt = :start, endzeitpunkt = :ende, ergebniszeitpunkt = :ergebnis WHERE test_formular.id = :id")
    void updateZeitpunkte(@Param("start") LocalDateTime startzeitpunkt,
                          @Param("ende")LocalDateTime endzeitpunkt,
                          @Param("ergebnis")LocalDateTime ergebniszeitpunkt,
                          @Param("id") Integer id);
}
