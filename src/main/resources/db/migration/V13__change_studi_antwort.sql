alter table studi_antwort
    drop column test_antwort,
    add column mc_antwort integer,
    add column freitext_antwort integer,
    add constraint fk_mc_antwort FOREIGN KEY (mc_antwort) REFERENCES mc_antwort(id),
    add constraint fk_freitext_antwort FOREIGN KEY (freitext_antwort) REFERENCES freitext_antwort(id);

