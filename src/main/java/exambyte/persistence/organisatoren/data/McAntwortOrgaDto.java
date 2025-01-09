package exambyte.persistence.organisatoren.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("mc_antwort_orga")
public record McAntwortOrgaDto(@Id Integer id,
                               String name,
                               boolean antwort,
                               Integer mcFrage,
                               Integer testFormular) {
}
