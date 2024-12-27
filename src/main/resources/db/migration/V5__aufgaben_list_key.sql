DROP TABLE studi_antwort CASCADE;

CREATE TABLE mc_aufgabe (
    id serial primary key,
    aufgabe varchar(255),
    punktzahl int,
    studi_test integer,
    foreign key (studi_test) references studi_test(id)
);

CREATE TABLE antwort_moeglichkeiten (
    antwort varchar(255),
    mc_aufgabe int,
    FOREIGN KEY (mc_aufgabe) REFERENCES mc_aufgabe(id)
);