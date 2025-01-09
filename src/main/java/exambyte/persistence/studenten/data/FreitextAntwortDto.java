package exambyte.persistence.studenten.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("freitext_antwort")
public record FreitextAntwortDto(@Id Integer id,
                                 String antworten,
                                 Integer studiTest,
                                 Integer aufgabeId,
                                 Integer studiId) {
}
