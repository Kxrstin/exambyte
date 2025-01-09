package exambyte.persistence.organisatoren.data;

import exambyte.persistence.studenten.data.McAntwortDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("mc_frage")
public record McFrageDto(Integer punkte,
                         String titel,
                         String fragestellung,
                         @Id Integer id,
                         String erklaerung,
                         List<McAntwortOrgaDto> mcAntwortOrga,
                         Integer testFormular) {
}
