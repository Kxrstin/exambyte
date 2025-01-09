package exambyte.persistence.organisatoren.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("freitext_frage")
public record FreitextFrageDto(Integer punkte,
                               String titel,
                               String fragestellung,
                               String erklaerung,
                               @Id Integer id,
                               Integer testFormular) {
}
