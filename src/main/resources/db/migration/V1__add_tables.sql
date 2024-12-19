create table test_daten(
    titel varchar(200) not null,
    startzeitpunkt timestamp not null,
    endzeitpunkt timestamp not null,
    ergebniszeitpunkt timestamp not null,
    testId integer primary key
);

create table studi_test(
    test_daten integer,
    id serial primary key,
    foreign key(test_daten) references test_daten(testId)
);

create table freitext_aufgabe(
    id serial primary key,
    aufgabe varchar(255) not null,
    punktzahl integer not null,
    studi_test integer not null,
    foreign key(studi_test) references studi_test(id)
);

create table studi_antwort(
    studi_id integer primary key,
    studi_test integer,
    foreign key(studi_test) references studi_test(id)
);

create table freitext_antwort(
    antwort varchar(255),
    id serial primary key,
    studi_antwort integer,
    studi_test integer,
    foreign key(studi_antwort) references studi_antwort(studi_id),
    foreign key(studi_test) references studi_test(id)
);

create table mc_antwort(
    id serial primary key,
    studi_antwort integer,
    studi_test integer,
    foreign key(studi_antwort) references studi_antwort(studi_id),
    foreign key(studi_test) references studi_test(id)
);
