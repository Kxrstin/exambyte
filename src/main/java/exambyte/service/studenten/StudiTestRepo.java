package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudiTestRepo extends CrudRepository<StudiTest, Integer> {
    StudiTest findById(int id);
    boolean existsBy(int id);

    @Query("SELECT s.* FROM studi_test s LEFT OUTER JOIN test_daten t ON s.test_daten = t.testId")
    List<StudiTest> findAll();

    StudiTest save(StudiTest test);
}
