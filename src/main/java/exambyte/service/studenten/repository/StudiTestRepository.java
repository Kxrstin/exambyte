package exambyte.service.studenten.repository;

import exambyte.aggregates.studenten.StudiTest.StudiTest;

import java.util.List;

public interface StudiTestRepository {
    StudiTest findById(int id);
    boolean existsById(int id);
    List<StudiTest> findAll();
    StudiTest save(StudiTest test);
}
