ALTER TABLE studi_test ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE IF EXISTS studi_test_id_seq;

ALTER TABLE studi_test
    ALTER COLUMN id SET DATA TYPE INTEGER;

ALTER TABLE studi_test
DROP CONSTRAINT IF EXISTS studi_test_test_daten_fkey;

-- Dann den Primary Key-Constraint entfernen und die neuen Spalten hinzufügen
ALTER TABLE test_daten
    DROP CONSTRAINT IF EXISTS test_daten_pkey,
    ADD COLUMN studi_test INTEGER,
    ADD CONSTRAINT fk_studi_test FOREIGN KEY (studi_test) REFERENCES studi_test(id);