drop table mc_antwort cascade;
drop table freitext_antwort cascade;
drop table studi_antwort cascade;

create table mc_antwort(
    id serial primary key,
    antworten varchar(2000),
    aufgabe_id integer,
    studi_id integer,
    studi_test integer,
    foreign key(studi_test) references studi_test(id)
);

create table freitext_antwort(
     id serial primary key,
     antworten varchar(2000),
     aufgabe_id integer,
     studi_id integer,
     studi_test integer,
     foreign key(studi_test) references studi_test(id)
);
