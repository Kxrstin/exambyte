package exambyte;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.aggregates.studenten.StudiTest.TestDaten;
import exambyte.service.studenten.StudiTestRepo;
import exambyte.service.studenten.TestDatenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

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
            StudiTest studiTest = new StudiTest(testDaten, null, 1);


            jdbc.update("DELETE FROM test_daten");
            jdbc.update("DELETE FROM studi_test WHERE id = 1");

            jdbc.update(
                    "INSERT INTO studi_test (test_daten, id) VALUES (?, ?)",
                    1,
                    studiTest.getId()
            );

            testRepo.save(studiTest);
        };
    }
}
