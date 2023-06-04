CREATE TABLE IF NOT EXISTS curr.statistic
(
    id bigint NOT NULL,
    date_curr date,
    abbreviation text,
    scale bigint,
    name text,
    official_rate double precision
);

ALTER TABLE IF EXISTS curr.statistic
    ADD CONSTRAINT statistic_weekend_id_fk FOREIGN KEY (weekend_id)
    REFERENCES curr.weekends (weekend_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;

