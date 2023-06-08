CREATE TABLE IF NOT EXISTS curr.statistic
(
    id bigint NOT NULL,
    date_curr date,
    abbreviation text,
    scale bigint,
    name text,
    official_rate double precision
);

