package exambyte.persistence.studenten.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("freitext_aufgabe")
public record FreitextAufgabeDto(@Id Integer id,
                                 String aufgabe,
                                 int punktzahl,
                                 Integer studiTest) {
}
