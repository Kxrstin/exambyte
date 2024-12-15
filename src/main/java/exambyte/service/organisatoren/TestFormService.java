package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestFormService {
    private TestFormRepo repository;
    private Map<Integer, TestFormular> zwischenspeicher = new HashMap<>();
    public TestFormService(TestFormRepo repository) {
        this.repository = repository;
    }

    public TestFormular getTestFormById(Integer id) {
        return zwischenspeicher.get(id);
    }

    public int addNewTestForm() {
        TestFormular testForm = new TestFormular("", new HashMap<>());
        zwischenspeicher.put(testForm.getId(), testForm);
        return testForm.getId();
    }

    public int saveTestForm(TestFormular testForm) {
        int id = testForm.getId();
        zwischenspeicher.put(id, testForm);
        System.out.println(testForm.getTestTitel());
        return id;
    }

    public List<TestFormular> getTestForms() {
        return zwischenspeicher.entrySet().stream()
                .map(entry -> entry.getValue())
                .toList();
    }

    public TestFormular getTestFormByIdDB(Integer id) {
        return repository.loadTestForm(id);
    }

    public int saveTestFormDB(TestFormular testForm) {
        return repository.saveTestForm(testForm);
    }

    public List<TestFormular> getTestFormsDB() {
        return repository.getTestForms();
    }
}
