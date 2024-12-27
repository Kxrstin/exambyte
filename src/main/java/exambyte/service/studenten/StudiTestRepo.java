package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface StudiTestRepo extends CrudRepository<StudiTest, Integer> {
    StudiTest findBy(int id);
    boolean existsBy(int id);
    List<StudiTest> findAll();
    StudiTest save(StudiTest test);
}
