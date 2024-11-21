package exambyte.infrastructure.studenten;

import exambyte.aggregates.studenten.StudiTest;
import exambyte.persistence.studenten.StudiTestDB;
import exambyte.service.studenten.StudiTestRepo;

import java.util.ArrayList;
import java.util.List;

public class StudiTestRepoImpl implements StudiTestRepo {
    private StudiTestDB studiDB;

    public StudiTestRepoImpl() {
        studiDB = new StudiTestDB();
    }

    @Override
    public StudiTest findTestById(int id) {
        return studiDB.getTestWithId(id);
    }

    @Override
    public boolean hasTestWithId(int id) {
        return studiDB.hasTestWithId(id);
    }

    @Override
    public List<StudiTest> getTestList() {
        return studiDB.getTestList();
    }
}
