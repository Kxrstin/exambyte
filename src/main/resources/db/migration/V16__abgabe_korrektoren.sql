create table abgabe(
    id serial primary key,
    studiantwort varchar(2000),
    aufgabe varchar(2000),
    aufgaben_id integer,
    studi_id integer,
    studi_test_titel varchar(200),
    feedback varchar(350),
    punktzahl integer,
    max_punktzahl integer,
    studi_test integer,
    antwort integer
);