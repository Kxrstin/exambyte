package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TestFormService {
    private TestFormRepo repository;

    public TestFormService(TestFormRepo repository) {
        this.repository = repository;
    }

    public TestFormular getTestFormById(Integer id) {
        return repository.loadTestForm(id);
    }

    public int addNewTestForm() {
        return repository.addNewTestForm();
    }

    public int saveTestForm(TestFormular testForm) {
        return repository.saveTestForm(testForm);
    }
}
