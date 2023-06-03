CREATE TABLE IF NOT EXISTS curr.currency
(
    id bigint,
    parent_id bigint,
    code bigint,
    abbreviation text,
    name text NOT NULL,
    name_bel text NOT NULL,
    name_eng text NOT NULL,
    quot_name text NOT NULL,
    quot_name_bel text NOT NULL,
    quot_name_eng text NOT NULL,
    name_multi text NOT NULL,
    name_bel_multi text NOT NULL,
    name_eng_multi text NOT NULL,
    scale bigint NOT NULL,
    periodicity bigint NOT NULL,
    date_start date NOT NULL,
    date_end date NOT NULL
);

