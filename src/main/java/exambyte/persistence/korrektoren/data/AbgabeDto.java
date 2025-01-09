package exambyte.persistence.korrektoren.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("abgabe")
public record AbgabeDto(@Id Integer id,
                        String studiantwort,
                        String aufgabe,
                        Integer aufgabenId,
                        Integer studiId,
                        String studiTestTitel,
                        String feedback,
                        Integer punktzahl,
                        Integer maxPunktzahl,
                        Integer studiTest,
                        Integer antwort) {
}
