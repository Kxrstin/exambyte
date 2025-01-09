package exambyte.service.organisatoren;

import exambyte.aggregates.organisatoren.TestFormular;

import java.time.LocalDateTime;
import java.util.List;

public interface TestFormRepository {
    TestFormular save(TestFormular testForm);
    TestFormular findById(int id);
    List<TestFormular> findAll();

    void updateZeitpunkte(LocalDateTime startzeitpunkt,
                          LocalDateTime endzeitpunkt,
                          LocalDateTime ergebniszeitpunkt,
                          Integer id);
}
