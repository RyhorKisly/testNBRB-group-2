CREATE TABLE IF NOT EXISTS curr.statistic
(
    id bigint NOT NULL,
    date_curr date NOT NULL,
    abbreviation text NOT NULL,
    scale bigint NOT NULL,
    name text NOT NULL,
    official_rate double precision NOT NULL
);

