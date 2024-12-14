package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<StudiTest> getBearbeitbareTests() {
        LocalDateTime now = LocalDateTime.now();
        return testRepo.loadTestList().stream()
                .filter(test -> test.isBearbeitbar(now))
                .toList();
    }

    public List<StudiTest> getAbgelaufeneTests() {
        LocalDateTime now = LocalDateTime.now();
        return testRepo.loadTestList().stream()
                .filter(test -> test.isAbgelaufen(now))
                .toList();
    }


    // Testinformationen parsen
    public String parseTitel(int id) {
        return "Titel: " + testRepo.loadTestWithId(id).getTitel();
    }
    public String parseStart(int id) {
        return "Startzeitpunkt: " + parseTime(testRepo.loadTestWithId(id).getStartzeitpunkt());
    }
    public String parseEnde(int id) {
        return "Endzeitpunkt: " + parseTime(testRepo.loadTestWithId(id).getEndzeitpunkt());
    }
    public String parseErgebnis(int id) {
        return "Ergebniszeitpunkt: " + parseTime(testRepo.loadTestWithId(id).getErgebnisZeitpunkt());
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
    public int getId(int testId) {
        return testRepo.loadTestWithId(testId).getId();
    }
    public boolean isAbgelaufen(int testId) {
        return getAbgelaufeneTests().contains(getTest(testId));
    }


    // Antworten speichern
    public void addAntwort(int testId, int aufgabeNr, String antwort, int studiId) {
        StudiTest test = testRepo.loadTestWithId(testId);
        if(test != null) {
            test.addAntwort(antwort, aufgabeNr, studiId);
        }
        testRepo.saveTest(test);
    }
    public String getAntwort(int testId, int aufgabe, int studiId) {
        StudiTest test = testRepo.loadTestWithId(testId);
        if(test != null) { return test.getAntwort(aufgabe, studiId);}
        return "";
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

    public String parseTime(LocalDateTime time) {
        String uhrzeit = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        String datum = time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return uhrzeit+ ", " + datum;
    }
}