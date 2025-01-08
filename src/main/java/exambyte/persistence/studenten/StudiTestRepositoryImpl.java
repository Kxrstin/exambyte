package exambyte.persistence.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.service.studenten.repository.StudiTestRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudiTestRepositoryImpl implements StudiTestRepository {
    private final StudiTestRepo repo;

    public StudiTestRepositoryImpl(StudiTestRepo repo) {
        this.repo = repo;
    }

    public StudiTest findById(int id) {
        return repo.findById(id);
    }

    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    public List<StudiTest> findAll() {
        return repo.findAll();
    }

    public StudiTest save(StudiTest test) {
        return repo.save(test);
    }

    public StudiTest saveNewTest(StudiTest test) {
        repo.insertNewTest(test.getTestDaten(), test.getId());
        return repo.save(test);
    }
}
