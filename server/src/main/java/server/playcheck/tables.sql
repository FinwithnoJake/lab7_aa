BEGIN;

CREATE TYPE government AS ENUM (
    'DESPOTISM', 'DEMOCRACY', 'DIARCHY', 'NOOCRACY', 'OLIGARCHY', 'PLUTOCRACY'
);


CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(52) UNIQUE NOT NULL,
    passw VARCHAR(255) NOT NULL,
    salt VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS city (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL CONSTRAINT not_empty_name CHECK(length(name) > 0),
    x INTEGER NOT NULL,
    y BIGINT NOT NULL,
    creation_date DATE DEFAULT NOW() NOT NULL,
    area FLOAT NOT NULL,
    population LONG NOT NULL,
    metersAboveSeaLevel FLOAT NOT NULL,
    carCode LONG NOT NULL,
    agglomeration LONG NOT NULL,
    Government government,
    human VARCHAR,
    owner_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

END;