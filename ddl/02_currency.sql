CREATE TABLE IF NOT EXISTS curr.currency
(
    id bigint NOT NULL,
    parent_id bigint,
    code bigint,
    abbreviation text,
    name text,
    name_bel text,
    name_eng text,
    quot_name text,
    quot_name_bel text,
    quot_name_eng text,
    name_multi text,
    name_bel_multi text,
    name_eng_multi text,
    scale bigint,
    periodicity bigint,
    date_start date,
    date_end date
);

