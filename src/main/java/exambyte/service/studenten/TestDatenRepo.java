package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest.TestDaten;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestDatenRepo extends CrudRepository<TestDaten, Integer> {
    TestDaten findByStudiTest(int id);
    boolean existsById(int id);
    List<TestDaten> findAll();
    TestDaten save(TestDaten testDaten);
}
