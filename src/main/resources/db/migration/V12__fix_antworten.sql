alter table mc_antwort
    add column aufgabe_id integer,
    add column studi_test_key integer,
    add column studi_test integer,
    add constraint fk_studi_test FOREIGN KEY (studi_test) REFERENCES studi_test(id);

alter table freitext_antwort
    add column aufgabe_id integer,
    add column studi_test_key integer,
    add column studi_test integer,
    add constraint fk_studi_test FOREIGN KEY (studi_test) REFERENCES studi_test(id);