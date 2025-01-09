package exambyte.persistence.studenten.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("mc_aufgabe")
public record McAufgabeDto(@Id Integer id,
                           String aufgabe,
                           List<AntwortMoeglichkeitenDto> antwortMoeglichkeitenDtos,
                           int punktzahl,
                           Integer studiTest) {
}
