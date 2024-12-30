BEGIN;
    DELETE FROM test_daten;
    DELETE FROM antwort_moeglichkeiten;
    DELETE FROM mc_aufgabe;
    DELETE FROM freitext_aufgabe;
    DELETE FROM freitext_antwort CASCADE;
    DELETE FROM mc_antwort CASCADE; COMMIT;
    DELETE FROM studi_test;