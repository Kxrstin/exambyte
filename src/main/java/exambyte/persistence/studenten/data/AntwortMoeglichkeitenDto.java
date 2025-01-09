package exambyte.persistence.studenten.data;

import org.springframework.data.relational.core.mapping.Table;

@Table("antwort_moeglichkeiten")
public record AntwortMoeglichkeitenDto(String antwort, Integer mcAufgabe) {
}
