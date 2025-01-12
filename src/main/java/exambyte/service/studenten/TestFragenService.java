package exambyte.service.studenten;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.aggregates.studenten.StudiTest.FreitextAufgabe;
import exambyte.aggregates.studenten.StudiTest.McAufgabe;
import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.service.studenten.loader.KorrekturenLoader;
import exambyte.service.studenten.repository.StudiTestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TestFragenService {
    private final StudiTestRepository studiTestRepository;
    private final KorrekturenLoader korrekturenLoader;

    public TestFragenService(StudiTestRepository testRepository, KorrekturenLoader korrekturenLoader) {
        this.studiTestRepository = testRepository;
        this.korrekturenLoader = korrekturenLoader;
    }

    public StudiTest save(StudiTest studiTest) {
        return studiTestRepository.save(studiTest);
    }

    public StudiTest getTest(int testId) {
        return studiTestRepository.findById(testId);
    }
    public boolean hasTestWithId(int testId) {
       return studiTestRepository.existsById(testId);
    }
    public List<StudiTest> getBearbeitbareTests() {
        LocalDateTime now = LocalDateTime.now();
        return studiTestRepository.findAll().stream()
                    .filter(test -> test.isBearbeitbar(now))
                    .toList();
    }
    public List<StudiTest> getAbgelaufeneTests() {
        return studiTestRepository.findAll().stream()
                .filter(test -> test.isAbgelaufen(LocalDateTime.now()))
                .toList();
    }


    // Testinformationen parsen
    public String parseTitel(int id) {
        try {
            return "Titel: " + studiTestRepository.findById(id).getTitel();
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }
    public String parseStart(int id) {
        try {
            return "Startzeitpunkt: " + parseTime(studiTestRepository.findById(id).getStartzeitpunkt());
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }
    public String parseEnde(int id) {
        try {
            return "Endzeitpunkt: " + parseTime(studiTestRepository.findById(id).getEndzeitpunkt());
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }
    public String parseErgebnis(int id) {
        try {
            return "Ergebniszeitpunkt: " + parseTime(studiTestRepository.findById(id).getErgebnisZeitpunkt());
        } catch (NullPointerException e) {
            return "Kein Studi vorhanden";
        }
    }


    // Test Infos
    public String getAufgabenstellung(int id, int nr) {
        try {
            return studiTestRepository.findById(id).getAufgabenstellung(nr);
        } catch (Exception e) {
            return "Kein Studi vorhanden";
        }
    }
    public int getPunktzahl(int id, int nr) {
        try {
            return studiTestRepository.findById(id).getPunktzahl(nr);
        } catch (NullPointerException e) {
            return 0;
        }
    }
    public boolean isFreitextAufgabe(int id, int nr) {
        try {
            return studiTestRepository.findById(id).isFreitextAufgabe(nr);
        } catch (NullPointerException e) {
            return false;
        }
    }
    public boolean isMCAufgabe(int id, int nr) {
        try {
            return studiTestRepository.findById(id).isMCAufgabe(nr);
        } catch (NullPointerException e) {
            return false;
        }
    }
    public List<String> getAntwortMoeglichkeiten(int id, int nr) {
        try {
            return studiTestRepository.findById(id).getAntwortmoeglichkeiten(nr);
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }
    public int getAnzahlAufgaben(int id) {
        try {
            return studiTestRepository.findById(id).getAnzahlAufgaben();
        } catch (NullPointerException e) {
            return 0;
        }
    }
    public boolean isAbgelaufen(int testId) {
        return studiTestRepository.findById(testId).isAbgelaufen(LocalDateTime.now());
    }


    // Antworten speichern
    public void addAntwort(int testId, int aufgabeId, String antwort, int studiId) {
        StudiTest test = studiTestRepository.findById(testId);
        test.addAntwort(antwort, aufgabeId, studiId);
        studiTestRepository.save(test);
    }
    public String getAntwort(int testId, int aufgabeId, int studiId) {
        try {
            return studiTestRepository.findById(testId).getAntwort(aufgabeId, studiId);
        } catch (NullPointerException e) {
            return "";
        }
    }

    // Zulassungsstatus und Ergebnis
    public String zulassungsStatus(Integer studiId) {
        int countBestanden = 0;
        List<Integer> idsAllerTests = new ArrayList<>();
        for(StudiTest studiTest: studiTestRepository.findAll()) {
            if(studiTest.getErgebnisZeitpunkt().isBefore(LocalDateTime.now())) {
                if (!idsAllerTests.contains(studiTest.getId())) {
                    idsAllerTests.add(studiTest.getId());
                }
                double erreichtePunktzahl = getErreichtePunkte(studiId, studiTest.getId());
                double maxPunktzahl = getMaxErreichbarePunkte(studiTest.getId());

                if (erreichtePunktzahl >= maxPunktzahl / 2) {
                    countBestanden++;
                }
            }
        }
        return zulassungsStatus(idsAllerTests.size(), countBestanden);

    }

    private String zulassungsStatus(int anzahlTestsGesamt, int countBestanden) {
        if(anzahlTestsGesamt == 14 && countBestanden < 14) {
        return "zulassungNichtErreicht";
    } else if(anzahlTestsGesamt == 14 && countBestanden == 14){
        return "zulassungErreicht";
    }
        if (countBestanden == anzahlTestsGesamt) {
            return "guterKurs";
        } else if (countBestanden == anzahlTestsGesamt - 1) {
            return "vorsicht";
        } else if (countBestanden == anzahlTestsGesamt - 2) {
            return "vorsichtiger";
        }
        return "fail";
    }

    public double getErgebnisInProzent(Integer studiId, Integer studiTest) {
        double sumErreichtePunkte = getErreichtePunkte(studiId, studiTest);
        double sumMaxPunkte = getMaxErreichbarePunkte(studiTest);
        if(sumMaxPunkte > 0) {
            return Math.round((sumErreichtePunkte / sumMaxPunkte) * 10000.0) / 100.0;
        }
        return 0.0;
    }
    public String parsePunktzahlFuerErgebnis(Integer studiId, Integer studiTest) {
        double sumErreichtePunkte =  getErreichtePunkte(studiId, studiTest);
        double sumMaxPunkte = getMaxErreichbarePunkte(studiTest);
        return sumErreichtePunkte + " / " + sumMaxPunkte + " Punkte";
    }
    public String testBestanden(Integer studiId, Integer studiTest) {
        if(getErgebnisInProzent(studiId, studiTest) >= 50.00

        ) {
            return "Super, Sie haben den Test bestanden!";
        }
        return "Sie haben den Test leider nicht bestanden.";
    }
    public double getMaxErreichbarePunkte(Integer studiTest) {
        double sumMaxPunkte = 0;
        for(McAufgabe aufgabe: studiTestRepository.findById(studiTest).getMcAufgaben()) {
            sumMaxPunkte += aufgabe.getPunktzahl();
        }
        for(FreitextAufgabe aufgabe: studiTestRepository.findById(studiTest).getFreitextAufgaben()) {
            sumMaxPunkte += aufgabe.getPunktzahl();
        }
        return sumMaxPunkte;
    }
    public double getErreichtePunkte(Integer studiId, Integer studiTest) {
        List<Abgabe> alleAbgabenFuerTest = korrekturenLoader.getKorrekturenFürTestVonStudi(studiId, studiTest);
        double sumErreichtePunkte = 0;

        for (Abgabe abgabe : alleAbgabenFuerTest) {
            sumErreichtePunkte += abgabe.getPunktzahl();
        }

        for(McAufgabe aufgabe: getTest(studiTest).getMcAufgaben()) {
            String gespeicherteAntwort = getAntwort(studiTest, aufgabe.getId(), studiId);
            if(gespeicherteAntwort.length() >= 2) {
                List<String> gewaehlteAntworten = Arrays.asList(gespeicherteAntwort
                        .substring(1, gespeicherteAntwort.length() - 1).split(", "));
                sumErreichtePunkte += getErreichtePunktzahlMcAufgabe(aufgabe.getId(), gewaehlteAntworten);
            }
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
    public LocalDateTime getErgebnisZeitpunkt(int testId) {
        return studiTestRepository.findById(testId).getErgebnisZeitpunkt();
    }
    public String getAufgabenTitel(Integer testId, Integer aufgabenId) {
        return studiTestRepository.findById(testId).getTitel(aufgabenId);
    }
    public String getErklaerungFürMcAufgabe(Integer aufgabeId) {
        return korrekturenLoader.getErklaerungFürMcAufgabe(aufgabeId);
    }
    public String getErklaerungFürFreitextAufgabe(Integer aufgabeId) {
        return korrekturenLoader.getErklaerungFürFreitextAufgabe(aufgabeId);
    }
    public String getKorrekturMcAufgabe(Integer aufgabenId, List<String> gewaehlteAntworten) {
        return korrekturenLoader.getKorrekturMcAufgabe(aufgabenId, gewaehlteAntworten);
    }
    public double getErreichtePunktzahlMcAufgabe(Integer aufgabenId, List<String> gewaehlteAntworten) {
        return korrekturenLoader.berechneErreichtePunktzahlMcAntwort(aufgabenId, gewaehlteAntworten);
    }
}