package exambyte.studenten;

import java.time.LocalDate;


public record TestStudenten(String titel,
                            LocalDate startzeitpunkt,
                            LocalDate endzeitpunkt,
                            LocalDate ergebniszeitpunkt,
                            int id) {
}
