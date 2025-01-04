alter table mc_antwort_orga
    add column test_formular integer,
    add constraint fk_test_formular foreign key (test_formular) references test_formular(id);