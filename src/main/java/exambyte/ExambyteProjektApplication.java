package exambyte;

import exambyte.aggregates.studenten.StudiTest.*;
import exambyte.service.studenten.StudiTestRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ExambyteProjektApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExambyteProjektApplication.class, args);
    }

    @Profile("!test") // Soll nicht bei Tests geladen werden
    @Bean
    public CommandLineRunner insertData(StudiTestRepo testRepo, JdbcTemplate jdbc) {
        return args -> {
           StudiTest studiTest1 = studiTest1();
            StudiTest studiTest2 = studiTest2();

            jdbc.update("BEGIN; DELETE FROM test_daten; " +
                            "DELETE FROM antwort_moeglichkeiten; " +
                            "DELETE FROM mc_aufgabe; " +
                            "DELETE FROM freitext_aufgabe; " +
                            "DELETE FROM freitext_antwort CASCADE; " +
                            "DELETE FROM mc_antwort CASCADE; COMMIT;");
            jdbc.update("DELETE FROM studi_test");

            jdbc.update("INSERT INTO studi_test (test_daten, id) VALUES (?, ?)",1, studiTest1.getId());
            jdbc.update("INSERT INTO studi_test (test_daten, id) VALUES (?, ?)",2,studiTest2.getId());

            testRepo.save(studiTest1);
            testRepo.save(studiTest2);
        };
    }




    public static StudiTest studiTest1() {
        TestDaten testDaten = new TestDaten("Mathematik für Informatik 3 Test 1",
                LocalDateTime.of(2024, 12, 14, 14, 0),
                LocalDateTime.of(2025, 1, 20, 14, 0),
                LocalDateTime.of(2025, 1, 30, 14, 0),
                1);

        FreitextAufgabe freitextAufgabe1 = new FreitextAufgabe("Was ist das Ergebnis von 1 + 1?", 2);
        FreitextAufgabe freitextAufgabe2 = new FreitextAufgabe("Was ist das Ergebnis von 2 + 15?", 1);
        McAufgabe mcAufgabe1 = new McAufgabe("Wofür steht DB?", List.of(new AntwortMoeglichkeiten("Datenbank"), new AntwortMoeglichkeiten("Deutsche Bahn"), new AntwortMoeglichkeiten("Dammer Berge")), 2, null);

        return new StudiTest(1, testDaten, List.of(mcAufgabe1), List.of(freitextAufgabe1, freitextAufgabe2));
    }

    public static StudiTest studiTest2() {
        TestDaten testDaten2 = new TestDaten("Wissenschaftliches Arbeiten",
                LocalDateTime.of(2024, 10, 16, 14, 0),
                LocalDateTime.of(2024, 12, 23, 14, 0),
                LocalDateTime.of(2025, 2, 26, 14, 0),
                2);

        FreitextAufgabe freitextAufgabe3 = new FreitextAufgabe("Wofür nutzt man Latex?", 2);
        FreitextAufgabe freitextAufgabe4 = new FreitextAufgabe("Wie erstellt man Tabellen mit Latex?", 1);
        McAufgabe mcAufgabe2 = new McAufgabe("Mir fällt keine Frage ein.", List.of(new AntwortMoeglichkeiten("A"), new AntwortMoeglichkeiten("B"), new AntwortMoeglichkeiten("C")), 2, null);

        return new StudiTest(2, testDaten2, List.of(mcAufgabe2), List.of(freitextAufgabe3, freitextAufgabe4));
    }
}