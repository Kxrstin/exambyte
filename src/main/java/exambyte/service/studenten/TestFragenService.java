package exambyte.service.studenten;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestFragenService {
    private final StudiTestRepo studiTestRepo;
    private final JdbcTemplate jdbc;
    private final KorrekturenLoader korrekturenLoader;

    @Autowired
    public TestFragenService(StudiTestRepo testRepo, JdbcTemplate jdbc, KorrekturenLoader korrekturenLoader) {
        this.studiTestRepo = testRepo;
        this.jdbc = jdbc;
        this.korrekturenLoader = korrekturenLoader;
    }

    public StudiTest save(StudiTest studiTest) {
        // Ohne die nächste Zeile, kommen immer Fehlermeldungen, da Spring Data die ID zu spät speichert
        jdbc.update("INSERT INTO studi_test (test_daten, id) VALUES (?, ?)",studiTest.getId(), studiTest.getId());
        return studiTestRepo.save(studiTest);
    }

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
            return "Startzeitpunkt: " + parseTime(studiTestRepo.findById(id).getStartzeitpunkt());
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }
    public String parseEnde(int id) {
        try {
            return "Endzeitpunkt: " + parseTime(studiTestRepo.findById(id).getEndzeitpunkt());
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }
    public String parseErgebnis(int id) {
        try {
            return "Ergebniszeitpunkt: " + parseTime(studiTestRepo.findById(id).getErgebnisZeitpunkt());
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }


    // Test Infos
    public String getAufgabe(int id, int nr) {
        try {
            return studiTestRepo.findById(id).getAufgabe(nr);
        } catch (Exception e) {
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
    public boolean isAbgelaufen(int testId) {
        return studiTestRepo.findById(testId).isAbgelaufen(LocalDateTime.now());
    }


    // Antworten speichern
    public void addAntwort(int testId, int aufgabeId, String antwort, int studiId) {
        StudiTest test = studiTestRepo.findById(testId);
        test.addAntwort(antwort, aufgabeId, studiId);
        studiTestRepo.save(test);
    }
    public String getAntwort(int testId, int aufgabeId, int studiId) {
        try {
            return studiTestRepo.findById(testId).getAntwort(aufgabeId, studiId);
        } catch (NullPointerException e) {
            return "";
        }
    }

    // Zulassungsstatus und Ergebnis
    public String zulassungsStatus(Integer studiId) {
        int countBestanden = 0;
        List<Abgabe> alleAbgabenVonStudi = korrekturenLoader.getAllKorrekturenFürStudi(studiId);
        List<Integer> idsAllerTests = new ArrayList<>();

        for(Abgabe abgabe: alleAbgabenVonStudi) {
            if(!idsAllerTests.contains(abgabe.getStudiTest())) {
                idsAllerTests.add(abgabe.getStudiTest());
            }
        }
        for (Integer studiTest : idsAllerTests) {
            int sumBewerteteAufgaben = 0;
            int sumMaxPunktzahl = 0;
            for(Abgabe abgabe: alleAbgabenVonStudi) {
                if (abgabe.getStudiTest().equals(studiTest)) {
                    sumBewerteteAufgaben += abgabe.getPunktzahl();
                    sumMaxPunktzahl += abgabe.getMaxPunktzahl();
                }
            }
            if(sumBewerteteAufgaben >= sumMaxPunktzahl / 2) {
                countBestanden++;
            }
        }
        if(idsAllerTests.size() == 14 && countBestanden < 14) {
            return "zulassungNichtErreicht";
        } else if(idsAllerTests.size() == 14 && countBestanden == 14){
            return "zulassungErreicht";
        }
        if (countBestanden == idsAllerTests.size()) {
            return "guterKurs";
        } else if (countBestanden == idsAllerTests.size() - 1) {
            return "vorsicht";
        } else if (countBestanden == idsAllerTests.size() - 2) {
            return "vorsichtiger";
        }
        return "fail";
    }
    public double getErgebnisInProzent(Integer studiId, Integer studiTest) {
        double sumErreichtePunkte = getErreichtePunkte(studiId, studiTest);
        double sumMaxPunkte = getMaxErreichbarePunkte(studiId, studiTest);
        if(sumMaxPunkte > 0) {
            return Math.round((sumErreichtePunkte / sumMaxPunkte) * 10000.0) / 100.0;
        }
        return 0.0;
    }
    public String parsePunktzahlFuerErgebnis(Integer studiId, Integer studiTest) {
        int sumErreichtePunkte = (int) getErreichtePunkte(studiId, studiTest);
        int sumMaxPunkte = (int) getMaxErreichbarePunkte(studiId, studiTest);
        return sumErreichtePunkte + " / " + sumMaxPunkte + " Punkte";
    }
    public String testBestanden(Integer studiId, Integer studiTest) {
        if(getErgebnisInProzent(studiId, studiTest) >= 0.5) {
            return "Super, Sie haben den Test bestanden!";
        }
        return "Sie haben den Test leider nicht bestanden.";
    }
    private double getMaxErreichbarePunkte(Integer studiId, Integer studiTest) {
        List<Abgabe> alleAbgabenFuerTest = korrekturenLoader.getKorrekturenFürTestVonStudi(studiId, studiTest);
        double sumMaxPunkte = 0;
        for(Abgabe abgabe: alleAbgabenFuerTest) {
            sumMaxPunkte += abgabe.getMaxPunktzahl();
        }
        return sumMaxPunkte;
    }
    private double getErreichtePunkte(Integer studiId, Integer studiTest) {
        List<Abgabe> alleAbgabenFuerTest = korrekturenLoader.getKorrekturenFürTestVonStudi(studiId, studiTest);
        double sumErreichtePunkte = 0;
        for (Abgabe abgabe : alleAbgabenFuerTest) {
            sumErreichtePunkte += abgabe.getPunktzahl();
        }
        return sumErreichtePunkte;
    }

    // Zeitdarstellung
    public String parseTime(LocalDateTime time) {
        String uhrzeit = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        String datum = time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return uhrzeit+ ", " + datum;
    }

    // Feedback und erreichte Punktzahl
    private Abgabe getAbgabeZuAufgabe(Integer testId, Integer aufgabenId, Integer studiId) {
        List<Abgabe> alleAbgabenVonTest = korrekturenLoader.getKorrekturenFürTestVonStudi(studiId, testId);
        Abgabe abgabeZuAufgabe = alleAbgabenVonTest.stream()
                .filter(abgabe -> abgabe.getAufgabenId().equals(aufgabenId))
                .findFirst()
                .orElse(null);
        return abgabeZuAufgabe;
    }
    public Integer getErreichtePunktzahl(Integer testId, Integer aufgabenId, Integer studiId) {
        if(getAbgabeZuAufgabe(testId, aufgabenId, studiId) == null) {
            return null;
        }
        return getAbgabeZuAufgabe(testId, aufgabenId, studiId).getPunktzahl();
    }
    public String getFeedback(Integer testId, Integer aufgabenId, Integer studiId) {
        if(getAbgabeZuAufgabe(testId, aufgabenId, studiId) == null) {
            return null;
        }
        return getAbgabeZuAufgabe(testId, aufgabenId, studiId).getFeedback();
    }
}