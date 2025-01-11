package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.FreitextFrage;
import exambyte.aggregates.organisatoren.McAntwortOrga;
import exambyte.aggregates.organisatoren.McFrage;
import exambyte.aggregates.organisatoren.TestFormular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TestFormService {
    private final TestFormRepository repository;
    private final Map<Integer, TestFormular> zwischenspeicher = new HashMap<>();

    @Autowired
    public TestFormService(TestFormRepository repository) {
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

    public int save(TestFormular testForm) {
        int id = testForm.getId();
        zwischenspeicher.put(id, testForm);
        return id;
    }

    public List<TestFormular> getTestForms() {
        return zwischenspeicher.entrySet().stream()
                .map(Map.Entry::getValue)
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


    // Code für Korrektur der Studi Abgaben
    public String getErklaerungFürMcAufgabe(Integer aufgabeId) {
        TestFormular form = getTestFormWithMcId(aufgabeId);
        if(form == null) { return ""; }
        return form.getMcFragen().stream()
                .filter(frage -> frage.getId().equals(aufgabeId))
                .map(McFrage::getErklaerung)
                .findFirst()
                .orElse("");
    }
    private TestFormular getTestFormWithMcId(Integer aufgabeId) {
        return repository.findAll()
                .stream()
                .filter(formular -> formular.getMcFragen()
                        .stream()
                        .filter(frage -> frage.getId().equals(aufgabeId))
                        .findFirst()
                        .orElse(null) != null)
                .findFirst()
                .orElse(null);
    }
    public String getErklaerungFürFreitextAufgabe(Integer aufgabeId) {
        TestFormular form = repository.findAll()
                .stream()
                .filter(formular -> formular.getFreitextFragen()
                        .stream()
                        .filter(frage -> frage.getId().equals(aufgabeId))
                        .findFirst()
                        .orElse(null) != null)
                .findFirst()
                .orElse(null);
        if(form == null) { return ""; }
        return form.getFreitextFragen().stream()
                .filter(frage -> frage.getId().equals(aufgabeId))
                .map(FreitextFrage::getErklaerung)
                .findFirst()
                .orElse("");
    }
    public boolean studiMcAntwortIsFalsch(Integer aufgabeId, String antwort) {
        TestFormular form = getTestFormWithMcId(aufgabeId);
        if(form == null) { return false; }
        McFrage mcFrage = form.getMcFragen().stream()
                .filter(frage -> frage.getId().equals(aufgabeId))
                .findFirst()
                .orElse(null);
        if(mcFrage == null) { return false; }
        return mcFrage.getMcAntwortOrga().stream()
                .filter(antwortOrga -> antwortOrga.getName().equals(antwort))
                .anyMatch(antwortOrga -> !antwortOrga.getAntwort());
    }
}
