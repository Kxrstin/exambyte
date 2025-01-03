package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // Mit DataJdbcTest gab es Probleme mit dem kompilieren der Flyway Versionen, die ich nicht beheben konnte.
@ActiveProfiles("test")
public class TestFragenServiceTest {
    @Autowired
    StudiTestRepo repo;

    @Test
    @DisplayName("Die getTest Methode gibt den korrekten Test mit den korrekten Test Daten zurück.")
    @Sql({"clear_data.sql", "add_daten.sql"})
    void test_getTest() {
        StudiTest testAusDb = repo.findById(0);

        assertThat(testAusDb.getId()).isEqualTo(0);
        assertThat(testAusDb.getTestDaten().getEndzeitpunkt()).isEqualTo(LocalDateTime.of(2025, 2, 10, 20, 30));
        assertThat(testAusDb.getTestDaten().getErgebniszeitpunkt()).isEqualTo(LocalDateTime.of(2025, 2, 20, 20, 30));
        assertThat(testAusDb.getTestDaten().getStartzeitpunkt()).isEqualTo(LocalDateTime.of(2024, 12, 30, 10, 30));
        assertThat(testAusDb.getTitel()).isEqualTo("Beispieltitel");
    }
}
