package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestFragenService {
    private final StudiTestRepo studiTestRepo;

    public TestFragenService(StudiTestRepo testRepo) {
        this.studiTestRepo = testRepo;
    }

    // StudiTest
    public StudiTest getTest(int testId) {
        return studiTestRepo.findById(testId);
    }

    public boolean hasTestWithId(int testId) {
       return studiTestRepo.existsById(testId);
    }

    public List<StudiTest> getBearbeitbareTests() {
        LocalDateTime now = LocalDateTime.now();
        return studiTestRepo.findAll().stream()
                    .filter(test -> test.isBearbeitbar(now))
                    .toList();
    }

    public List<StudiTest> getAbgelaufeneTests() {
        LocalDateTime now = LocalDateTime.now();
        return studiTestRepo.findAll().stream()
                .filter(test -> test.isAbgelaufen(now))
                .toList();
    }


    // Testinformationen parsen
    public String parseTitel(int id) {
        try {
            return "Titel: " + studiTestRepo.findById(id).getTitel();
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }

    public String parseStart(int id) {
        try {
            return "Startzeitpunkt: " + studiTestRepo.findById(id).getStartzeitpunkt();
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }

    public String parseEnde(int id) {
        try {
            return "Endzeitpunkt: " + studiTestRepo.findById(id).getEndzeitpunkt();
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }
    public String parseErgebnis(int id) {
        try {
            return "Ergebniszeitpunkt: " + studiTestRepo.findById(id).getErgebnisZeitpunkt();
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }


    // Test Infos
    public String getAufgabe(int id, int nr) {
        try {
            return studiTestRepo.findById(id).getAufgabe(nr);
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }
    public int getPunktzahl(int id, int nr) {
        try {
            return studiTestRepo.findById(id).getPunktzahl(nr);
        } catch (NullPointerException e) {
            return 0;
        }
    }
    public boolean isFreitextAufgabe(int id, int nr) {
        try {
            return studiTestRepo.findById(id).isFreitextAufgabe(nr);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isMCAufgabe(int id, int nr) {
        try {
            return studiTestRepo.findById(id).isMCAufgabe(nr);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public List<String> getAntwortMoeglichkeiten(int id, int nr) {
        try {
            return studiTestRepo.findById(id).getAntwortmoeglichkeiten(nr);
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

    public int getAnzahlAufgaben(int id) {
        try {
            return studiTestRepo.findById(id).getAnzahlAufgaben();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    // TODO sinnlos?
    public Integer getId(int testId) {
        try {
            return studiTestRepo.findById(testId).getId();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public boolean isAbgelaufen(int testId) {
        return getAbgelaufeneTests().contains(getTest(testId));
    }


    // Antworten speichern
    public void addAntwort(int testId, int aufgabeNr, String antwort, int studiId) {
        StudiTest test = studiTestRepo.findById(testId);
        try {
            test.addAntwort(antwort, aufgabeNr, studiId);
        } catch (NullPointerException e) {
            return;
        }
        studiTestRepo.save(test);
    }
    public String getAntwort(int testId, int aufgabe, int studiId) {
        try {
            return studiTestRepo.findById(testId).getAntwort(aufgabe, studiId);
        } catch (NullPointerException e) {
            return "";
        }
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