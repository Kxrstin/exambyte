package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestFormRepo extends CrudRepository<TestFormular, Integer> {
    TestFormular save(TestFormular testForm);
    TestFormular findById(int id);
    List<TestFormular> findAll();
}
