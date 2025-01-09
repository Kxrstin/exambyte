package exambyte.persistence.organisatoren.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table("test_formular")
public record TestFormularDto(@Id Integer id,
                              String test_titel,
                              LocalDateTime startzeitpunkt,
                              LocalDateTime endzeitpunkt,
                              LocalDateTime ergebniszeitpunkt,
                              List<McFrageDto> mcFragen,
                              List<FreitextFrageDto> freitextFragen,
                              List<McAntwortOrgaDto> mcAntwortOrga) {
}
