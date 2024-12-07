package exambyte.persistence.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.service.organisatoren.TestFormRepo;
import exambyte.service.organisatoren.TestFormService;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TestFormRepoImpl implements TestFormRepo {
    private Map<Integer, TestFormular> testForms = new HashMap<>();

    public int saveTestForm(TestFormular testForm) {
        int id = testForm.getId();
        testForms.put(id, testForm);
        return id;
    }

    public TestFormular loadTestForm(Integer id) {
        return testForms.get(id);
    }

    public int addNewTestForm() {
        TestFormular testForm = new TestFormular("", new HashMap<>());
        testForms.put(testForm.getId(), testForm);
        return testForm.getId();
    }

    public List<TestFormular> getTestForms() {
        return testForms.entrySet().stream()
                .map(entry -> entry.getValue())
                .toList();
    }
}
