BEGIN;
    DELETE FROM test_daten CASCADE;
    DELETE FROM antwort_moeglichkeiten CASCADE;
    DELETE FROM mc_aufgabe CASCADE;
    DELETE FROM freitext_aufgabe CASCADE;
    DELETE FROM freitext_antwort CASCADE;
    DELETE FROM mc_antwort CASCADE;
COMMIT;
DELETE FROM studi_test;