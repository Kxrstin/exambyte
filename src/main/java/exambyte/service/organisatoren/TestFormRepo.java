package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//TODO: CrudRepo extenden
public interface TestFormRepo {
    TestFormular save(TestFormular testForm);
    TestFormular findBy(int id);
    List<TestFormular> findAll();
}
