package exambyte.aggregates.studenten;

import java.time.LocalDate;
// TODO Überarbeiten

public record TestStudenten(String titel,
                            LocalDate startzeitpunkt,
                            LocalDate endzeitpunkt,
                            LocalDate ergebniszeitpunkt,
                            int id) {
}
