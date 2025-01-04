/*package exambyte.persistence.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.service.organisatoren.TestFormRepo;
import exambyte.service.organisatoren.TestFormService;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TestFormRepoImpl {
    private Map<Integer, TestFormular> testForms = new HashMap<>();

    public TestFormular save(TestFormular testForm) {
        int id = testForm.getId();
        testForms.put(id, testForm);
        return testForm;
    }

    public TestFormular findBy(int id) {
        return testForms.get(id);
    }

    public int addNewTestForm() {
        TestFormular testForm = new TestFormular("", new ArrayList<>(), new ArrayList<>());
        testForms.put(testForm.getId(), testForm);
        return testForm.getId();
    }

    public List<TestFormular> findAll() {
        return testForms.entrySet().stream()
                .map(entry -> entry.getValue())
                .toList();
    }
}*/
