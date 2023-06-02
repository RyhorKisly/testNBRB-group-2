CREATE DATABASE nbrb
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251@icu'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE currencies (
    cur_id INT PRIMARY KEY,
    cur_parent_id INT,
    cur_code VARCHAR(10),
    cur_abbreviation VARCHAR(10),
    cur_name VARCHAR(100),
    cur_name_bel VARCHAR(100),
    cur_name_eng VARCHAR(100),
    cur_quot_name VARCHAR(100),
    cur_quot_name_bel VARCHAR(100),
    cur_quot_name_eng VARCHAR(100),
    cur_name_multi VARCHAR(100),
    cur_name_bel_multi VARCHAR(100),
    cur_name_eng_multi VARCHAR(100),
    cur_scale INT,
    cur_periodicity INT,
    cur_date_start VARCHAR(20),
    cur_date_end VARCHAR(20)
);

CREATE TABLE rates (
    id SERIAL PRIMARY KEY,
    cur_id INT,
    date DATE,
    cur_abbreviation VARCHAR(10),
    cur_scale INT,
    cur_name VARCHAR(100),
    cur_official_rate NUMERIC,
    UNIQUE (cur_id, date),
    FOREIGN KEY (cur_id) REFERENCES currencies (cur_id)
);