create table studi_antwort(
    id serial primary key,
    studi_id integer,
    studi_test integer,
    test_antwort integer,
    test_aufgabe integer,
    foreign key(studi_test) references studi_test(id)
);

drop table mc_antwort;
drop table freitext_antwort;

create table mc_antwort(
    id serial primary key,
    studi_antwort integer,
    antworten varchar(2000),
    foreign key(studi_antwort) references studi_antwort(id)
);

create table freitext_antwort(
    id serial primary key,
    antwort varchar(500),
    studi_antwort integer,
    foreign key(studi_antwort) references studi_antwort(id)
);