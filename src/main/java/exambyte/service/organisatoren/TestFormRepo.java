package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.stereotype.Repository;

@Repository
public interface TestFormRepo {
    String saveTestForm(TestFormular testForm);
    TestFormular loadTestForm(String id);
}
