create table test_formular(
      id serial primary key,
      test_titel varchar(200),
      startzeitpunkt timestamp,
      endzeitpunkt timestamp,
      ergebniszeitpunkt timestamp
);

create table mc_antwort_orga(
    id serial primary key,
    name varchar(200),
    antwort boolean
);

create table mc_frage (
    punkte integer,
    titel varchar(200),
    fragestellung varchar(200),
    erklaerung varchar(500),
    id serial primary key,
    antworten integer,
    test_formular integer,
    test_formular_key integer,
    foreign key (test_formular) references test_formular(id),
    foreign key (antworten) references mc_antwort_orga(id)
);

create table freitext_frage (
    punkte integer,
    titel varchar(200),
    fragestellung varchar(200),
    erklaerung varchar(500),
    id serial primary key,
    test_formular integer,
    test_formular_key integer,
    foreign key (test_formular) references test_formular(id)
);

alter table mc_antwort_orga
    add column mc_frage integer,
    add constraint fk_mc_frage foreign key (mc_frage) references mc_frage(id);