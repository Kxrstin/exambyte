package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // Mit DataJdbcTest gab es Probleme mit dem kompilieren der Flyway Versionen, die ich nicht beheben konnte.
@ActiveProfiles("test")
public class StudiTestRepoTest {
    @Autowired
    StudiTestRepo repo;

    @Test
    @DisplayName("Die getTest Methode gibt den korrekten Test mit den korrekten Test Daten zurück.")
    @Sql({"clear_data.sql", "add_studi_test_mit_testdaten.sql"})
    void test_findById() {
        StudiTest testAusDb = repo.findById(0);

        assertThat(testAusDb.getId()).isEqualTo(0);
        assertThat(testAusDb.getTestDaten().getEndzeitpunkt()).isEqualTo(LocalDateTime.of(2025, 2, 10, 20, 30));
        assertThat(testAusDb.getTestDaten().getErgebniszeitpunkt()).isEqualTo(LocalDateTime.of(2025, 2, 20, 20, 30));
        assertThat(testAusDb.getTestDaten().getStartzeitpunkt()).isEqualTo(LocalDateTime.of(2024, 12, 30, 10, 30));
        assertThat(testAusDb.getTitel()).isEqualTo("Beispieltitel");
    }

    @Test
    @DisplayName("Die getTest Methode gibt den korrekten Test mit den korrekten Test Daten zurück.")
    @Sql({"clear_data.sql", "add_studi_test_mit_testdaten.sql", "add_freitext_aufgabe.sql"})
    void test_addAntwortZuFreitextAufgabe() {
        StudiTest testAusDb = repo.findById(0);
        List<Integer> aufgabenIds = testAusDb.getAufgabenIds();
        testAusDb.addAntwort("Dies ist eine Beispielantwort", aufgabenIds.get(0), 1234);

        assertThat(testAusDb.getAntwort(aufgabenIds.get(0), 1234)).isEqualTo("Dies ist eine Beispielantwort");
    }

    @Test
    @DisplayName("Die getTest Methode gibt den korrekten Test mit den korrekten Test Daten zurück.")
    @Sql({"clear_data.sql", "add_studi_test_mit_testdaten.sql", "add_mc_aufgabe.sql"})
    void test_addAntwortZuMcAufgabe() {
        StudiTest testAusDb = repo.findById(0);
        List<Integer> aufgabenIds = testAusDb.getAufgabenIds();
        testAusDb.addAntwort("[Dies ist eine Beispielantwort]", aufgabenIds.get(0), 1234);

        assertThat(testAusDb.getAntwort(aufgabenIds.get(0), 1234)).isEqualTo("[Dies ist eine Beispielantwort]");
    }
}
