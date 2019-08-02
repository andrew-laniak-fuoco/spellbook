-- psql -h PSQL_HOST -p 5432 -U postgres jrvstrading -f schema.sql
-- Drop table

DROP TABLE IF EXISTS public.spell cascade;

CREATE TABLE public.spell
(
    name        varchar NOT NULL,
    school      varchar NOT NULL,
    slevel      int4    NOT NULL,
    range       varchar NOT NULL,
    cast_time   varchar NOT NULL,
    component   varchar NOT NULL,
    duration    varchar NOT NULL,
    sdesc       varchar NOT NULL,
    higher      varchar,
    CONSTRAINT spell_pk PRIMARY KEY (name)
);
