package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TestFormService {
    private TestFormRepo repository;
    private Map<Integer, TestFormular> zwischenspeicher = new HashMap<>();

    @Autowired
    public TestFormService(TestFormRepo repository) {
        this.repository = repository;
    }

    public TestFormular getTestFormById(Integer id) {
        return zwischenspeicher.get(id);
    }

    public int addNewTestForm() {
        Integer id = UUID.randomUUID().hashCode();
        TestFormular testForm = new TestFormular(id,"", new ArrayList<>(), new ArrayList<>());
        zwischenspeicher.put(id, testForm);
        return testForm.getId();
    }

    public void deleteTestDB(Integer id) {
        repository.deleteById(id);
    }

    public int save(TestFormular testForm) {
        int id = testForm.getId();
        zwischenspeicher.put(id, testForm);
        return id;
    }

    public List<TestFormular> getTestForms() {
        return zwischenspeicher.entrySet().stream()
                .map(entry -> entry.getValue())
                .toList();
    }

    public TestFormular getTestFormByIdDB(int id) {
        return repository.findById(id);
    }

    public void saveTestFormDBWithNewZeitpunkten(TestFormular testForm) {
        repository.updateZeitpunkte(testForm.getStartzeitpunkt(), testForm.getEndzeitpunkt(), testForm.getErgebniszeitpunkt(), testForm.getId());
    }

    public TestFormular saveTestFormDB(TestFormular testForm) {
        return repository.save(testForm);
    }

    public List<TestFormular> getTestFormsDB() {
        return repository.findAll();
    }

    public List<TestFormular> getTestFormsDBFuerStudis() {
        return repository.findAll().stream()
                .filter(formular -> LocalDateTime.now().isAfter(formular.getStartzeitpunkt()))
                .toList();
    }
}
