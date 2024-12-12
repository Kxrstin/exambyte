package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TestFormRepo {
    int saveTestForm(TestFormular testForm);
    TestFormular loadTestForm(Integer id);
    int addNewTestForm();
    List<TestFormular> getTestForms();
}
