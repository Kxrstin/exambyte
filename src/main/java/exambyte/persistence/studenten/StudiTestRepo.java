package exambyte.persistence.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.aggregates.studenten.StudiTest.TestDaten;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudiTestRepo extends CrudRepository<StudiTest, Integer> {
    StudiTest findById(int id);
    boolean existsById(int id);
    List<StudiTest> findAll();
    StudiTest save(StudiTest test);

    @Modifying
    @Query("INSERT INTO studi_test (test_daten, id) VALUES (:daten, :id)")
    StudiTest insertNewTest(@Param("daten") TestDaten daten, @Param("id") Integer id);
}
