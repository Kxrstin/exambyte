package exambyte.service.studenten.repository;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudiTestRepo extends CrudRepository<StudiTest, Integer> {
    StudiTest findById(int id);
    boolean existsById(int id);
    List<StudiTest> findAll();
    StudiTest save(StudiTest test);
}
