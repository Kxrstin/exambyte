package exambyte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class ExambyteProjektApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExambyteProjektApplication.class, args);
    }
}