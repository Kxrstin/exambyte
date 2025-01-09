package exambyte.persistence.studenten.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("studi_test")
public record StudiTestDto(@Id Integer id,
                           TestDatenDto testDaten,
                           List<FreitextAufgabeDto> freitextAufgaben,
                           List<McAufgabeDto> mcAufgaben,
                           List<McAntwortDto> mcAntworten,
                           List<FreitextAntwortDto> freitextAntworten) {
}
