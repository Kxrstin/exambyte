package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestFormRepo {
    private Map<String, TestFormular> testForms = new HashMap<>();

    public String saveTestForm(TestFormular testForm) {
        String id = UUID.randomUUID().toString();
        testForms.put(id, testForm);
        return id;
    }

    public TestFormular loadTestForm(String id) {
        return testForms.get(id);
    }
}
