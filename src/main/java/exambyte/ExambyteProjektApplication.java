package exambyte;

import exambyte.aggregates.studenten.StudiTest.*;
import exambyte.service.studenten.StudiTestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ExambyteProjektApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExambyteProjektApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbc;

    @Bean
    public CommandLineRunner insertData(StudiTestRepo testRepo) {
        return args -> {
            TestDaten testDaten = new TestDaten("Mathematik für Informatik 3 Test 1",
                    LocalDateTime.of(2024, 12, 14, 14, 0),
                    LocalDateTime.of(2025, 1, 20, 14, 0),
                    LocalDateTime.of(2025, 1, 30, 14, 0),
                    1);

            FreitextAufgabe freitextAufgabe1 = new FreitextAufgabe("Was ist das Ergebnis von 1 + 1?", 2);
            FreitextAufgabe freitextAufgabe2 = new FreitextAufgabe("Was ist das Ergebnis von 2 + 15?", 1);
            McAufgabe mcAufgabe1 = new McAufgabe("Wofür steht DB?", List.of(new AntwortMoeglichkeiten("Datenbank"), new AntwortMoeglichkeiten("Deutsche Bahn"), new AntwortMoeglichkeiten("Dammer Berge")), 2, 1);

            StudiTest studiTest = new StudiTest(1, testDaten, List.of(mcAufgabe1), List.of(freitextAufgabe1, freitextAufgabe2));
            jdbc.update("DELETE FROM test_daten");
            jdbc.update("DELETE FROM antwort_moeglichkeiten");
            jdbc.update("DELETE FROM freitext_aufgabe");
            jdbc.update("DELETE FROM mc_aufgabe");
            jdbc.update("DELETE FROM antwort_moeglichkeiten");
            jdbc.update("DELETE FROM studi_test WHERE id = 1");

            jdbc.update(
                    "INSERT INTO studi_test (test_daten, id) VALUES (?, ?)",
                    1,
                    studiTest.getId()
            );

//            jdbc.update("INSERT INTO freitext_aufgabe (aufgabe, punktzahl, studi_test) VALUES (?, ?, ?)",
//                    freitextAufgabe1.getAufgabe(),
//                    freitextAufgabe1.getPunktzahl(),
//                    1
//            );
//
//            jdbc.update("INSERT INTO freitext_aufgabe (aufgabe, punktzahl, studi_test) VALUES (?, ?, ?)",
//                    freitextAufgabe2.getAufgabe(),
//                    freitextAufgabe2.getPunktzahl(),
//                    1
//            );
//
//            jdbc.update("INSERT INTO mc_aufgabe (aufgabe, punktzahl, studi_test) VALUES (?, ?, ?)",
//                    mcAufgabe1.getAufgabe(),
//                    mcAufgabe1.getPunktzahl(),
//                    1
//            );
//
//            Integer mc = jdbc.query("SELECT id FROM mc_aufgabe WHERE studi_test = 1", rs -> rs.next() ? rs.getInt("id"): null);
//
//            for(String antwortmoeglichkeit: mcAufgabe1.getAntwortMoeglichkeiten())
//            jdbc.update("INSERT INTO antwort_moeglichkeiten (antwort, mc_aufgabe) VALUES (?, ?)",
//                    antwortmoeglichkeit,
//                    mc
//            );
            testRepo.save(studiTest);
        };
    }
}
