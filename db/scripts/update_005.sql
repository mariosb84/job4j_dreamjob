CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  email varchar(255) UNIQUE,
  password TEXT
);