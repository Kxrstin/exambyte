package exambyte.studenten;

import java.time.LocalDate;
import java.util.List;

public class TestMother {
    public static List<TestStudenten> testListe() {
        return List.of(new TestStudenten("Test Dummy 1",
                    LocalDate.of(2024,10,10),
                    LocalDate.of(2024,10,20),
                    LocalDate.of(2024,10,30),
                    1),
                new TestStudenten("Test Dummy 2",
                    LocalDate.of(2024,11,10),
                    LocalDate.of(2024,11,20),
                    LocalDate.of(2024,11,30),
                    2),
                new TestStudenten("Test Dummy 3",
                        LocalDate.of(2024,12,10),
                        LocalDate.of(2024,12,20),
                        LocalDate.of(2024,12,30),
                        3),
                new TestStudenten("Test Dummy 4",
                        LocalDate.of(2025,1,10),
                        LocalDate.of(2025,1,20),
                        LocalDate.of(2025,1,30),
                        4));
    }
}
