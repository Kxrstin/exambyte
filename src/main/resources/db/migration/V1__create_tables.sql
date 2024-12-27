create table TestForm(
    titel varchar(200) not null,
    startzeitpunkt timestamp not null,
    endzeitpunkt timestamp not null,
    ergebniszeitpunkt timestamp not null,
    testId integer primary key
);

create table StudiTest(
    test_form integer not null,
    id serial primary key,
    foreign key (test_form) references TestForm(testId)
);

create table FreitextAufgabe(
    id serial primary key,
    aufgabe varchar(255) not null,
    punktzahl integer not null,
    studi_test integer not null,
    foreign key (studi_test) references StudiTest(id)
);

create table StudiAntwort(
    studi_id integer primary key,
    studi_test integer,
    foreign key (studi_test) references StudiTest(id)
);

create table FreitextAntwort(
    antwort varchar(255),
    id serial primary key,
    studi_antwort integer,
    studi_test integer,
    foreign key (studi_antwort) references StudiAntwort(studi_id),
    foreign key (studi_test) references StudiTest(id)
);

create table McAntwort(
    id serial primary key,
    studi_antwort integer,
    studi_test integer,
    foreign key (studi_antwort) references StudiAntwort(studi_id),
    foreign key (studi_test) references StudiTest(id)
);