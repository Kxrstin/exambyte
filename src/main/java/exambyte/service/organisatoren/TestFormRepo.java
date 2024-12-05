package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.stereotype.Repository;


public interface TestFormRepo {
    int saveTestForm(TestFormular testForm);
    TestFormular loadTestForm(Integer id);
    int addNewTestForm();
}
