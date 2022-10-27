CREATE TABLE if not exists candidates (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created timestamp,
   photo BYTEA
);