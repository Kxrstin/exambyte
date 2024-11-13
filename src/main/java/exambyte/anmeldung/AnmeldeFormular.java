package exambyte.anmeldung;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnmeldeFormular(@NotBlank(message = "Die Eingabe darf nicht leer sein")
                              @NotNull(message = "Bitte geben Sie Ihre Email Adresse ein")
                              @Email(message = "Bitte geben Sie eine gültige Email Adresse ein")
                              String mail,
                              @NotBlank(message = "Die Eingabe darf nicht leer sein")
                              @NotNull(message = "Bitte geben Sie Ihr Passwort ein")
                              String password) {
    // TODO Security
}
