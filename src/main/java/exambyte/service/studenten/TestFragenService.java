package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TestFragenService {
    private final StudiTestRepo testRepo;

    public TestFragenService(StudiTestRepo testRepo) {
        this.testRepo = testRepo;
    }

    // StudiTest
    public StudiTest getTest(int testId) {
        return testRepo.loadTestWithId(testId);
    }
    public boolean hasTestWithId(int testId) {
       return testRepo.hasTestWithId(testId);
    }
    public List<StudiTest> getTestList() {
        return testRepo.loadTestList();
    }


    // Testinformationen parsen
    public String parseTitel(StudiTest test) {
        return "Titel: " + test.getTitel();
    }
    public String parseStart(StudiTest test) {
        return "Startzeitpunkt: " + test.getStartzeitpunkt().format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
    }
    public String parseEnde(StudiTest test) {
        return "Endzeitpunkt: " + test.getEndzeitpunkt().format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
    }
    public String parseErgebnis(StudiTest test) {
        return "Ergebniszeitpunkt: " + test.getErgebnisZeitpunkt().format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
    }


    // Test Infos
    public String getAufgabe(int id, int nr) {
        return testRepo.loadTestWithId(id).getAufgabe(nr);
    }
    public int getPunktzahl(int id, int nr) {
        return testRepo.loadTestWithId(id).getPunktzahl(nr);
    }
    public boolean isFreitextAufgabe(int id, int nr) {
        return testRepo.loadTestWithId(id).isFreitextAufgabe(nr);
    }
    public boolean isMCAufgabe(int id, int nr) {
        return testRepo.loadTestWithId(id).isMCAufgabe(nr);
    }
    public List<String> getAntwortMoeglichkeiten(int id, int nr) {
        return testRepo.loadTestWithId(id).getAntwortmoeglichkeiten(nr);
    }
    public int getAnzahlAufgaben(int id) {
        return testRepo.loadTestWithId(id).getAnzahlAufgaben();
    }


    // TODO: Gehört nicht hier hin
    public String zulassungsStatus(List<StudiTest> tests) {
        int countBestanden = 0;
        for (StudiTest test : tests) {
            if (test.testBestanden()) {
                countBestanden++;
            }
        }
        if(tests.size() == 14 && countBestanden < 14) {
            return "zulassungNichtErreicht";
        } else if(tests.size() == 14 && countBestanden == 14){
            return "zulassungErreicht";
        }
        if (countBestanden == tests.size()) {
            return "guterKurs";
        } else if (countBestanden == tests.size() - 1) {
            return "vorsicht";
        } else if (countBestanden == tests.size() - 2) {
            return "vorsichtiger";
        }
        return "fail";
    }
}