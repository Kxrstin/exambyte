package exambyte;

// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExambyteProjektApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExambyteProjektApplication.class, args);
    }

//    @Profile("!test") // Soll nicht bei Tests geladen werden
//    @Bean
//    public CommandLineRunner deleteData(JdbcTemplate jdbc) {
//        return args -> {
//            jdbc.update("BEGIN; DELETE FROM test_daten; " +
//                    "DELETE FROM antwort_moeglichkeiten; " +
//                    "DELETE FROM mc_aufgabe; " +
//                    "DELETE FROM freitext_aufgabe; " +
//                    "DELETE FROM freitext_antwort CASCADE; " +
//                    "DELETE FROM mc_antwort CASCADE; " +
//                    "DELETE FROM abgabe;" +
//                    "DELETE FROM mc_antwort_orga;" +
//                    "DELETE FROM mc_frage;" +
//                    "DELETE FROM freitext_frage;" +
//                    "DELETE FROM test_formular;" +
//                    "COMMIT;");
//            jdbc.update("DELETE FROM studi_test");
//       };
//   }
}