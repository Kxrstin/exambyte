package exambyte.persistence.studenten.data;

import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("test_daten")
public record TestDatenDto(String titel,
                           LocalDateTime startzeitpunkt,
                           LocalDateTime endzeitpunkt,
                           LocalDateTime ergebniszeitpunkt,
                           Integer studiTest) {
}
