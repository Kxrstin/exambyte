package exambyte.persistence.studenten.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("mc_antwort")
public record McAntwortDto(@Id Integer id,
                           String antworten,
                           Integer aufgabeId,
                           Integer studiId,
                           Integer studiTest) {
}
